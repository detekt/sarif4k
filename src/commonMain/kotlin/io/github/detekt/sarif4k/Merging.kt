@file:JvmName("Merging")

package io.github.detekt.sarif4k

import kotlin.jvm.JvmName

fun SarifSchema210.merge(other: SarifSchema210): SarifSchema210 {
    require(schema == other.schema) { "Cannot merge sarifs with different schemas: '$schema' or '${other.schema}'" }
    require(version == other.version) { "Cannot merge sarifs with different versions: '$version' or '${other.version}'" }

    val mergedExternalProperties = (inlineExternalProperties.orEmpty() + other.inlineExternalProperties.orEmpty())
        .takeIf { it.isNotEmpty() }

    val mergedProperties = when {
        properties != null && other.properties != null -> properties.merge(other.properties)
        properties != null -> properties
        other.properties != null -> other.properties
        else -> null
    }

    val mergedRuns = runs.merge(other.runs)

    return SarifSchema210(
        schema,
        version,
        mergedExternalProperties,
        mergedProperties,
        mergedRuns
    )
}

private fun PropertyBag.merge(other: PropertyBag): PropertyBag {
    val aTags = this["tags"] as? Collection<*>
    val bTags = other["tags"] as? Collection<*>
    val tags = if (aTags != null && bTags != null) {
        mapOf("tags" to (aTags + bTags).distinct())
    } else {
        emptyMap()
    }

    return PropertyBag(this + other + tags)
}

private fun List<Run>.merge(other: List<Run>): List<Run> {
    val runsByTool = (this + other).groupBy { it.tool.driver.fullName }

    return runsByTool.mapValues { (_, runs) ->
        val baseRun = runs.firstOrNull() ?: return@mapValues null

        val mergedRules = runs.flatMap { it.tool.driver.rules.orEmpty() }.distinctBy { it.id }

        val (mergedOriginalURIBaseIDs, runKeyMappings) = mergeOriginalURIBaseIDs(runs)

        val mergedResults = runs.flatMapIndexed { runIndex, run ->
            run.results.orEmpty().map { result ->
                val keyMappings = runKeyMappings.getOrNull(runIndex) ?: emptyMap()
                result.updateArtifactLocationBaseIds(keyMappings)
            }
        }

        baseRun.copy(
            originalURIBaseIDS = mergedOriginalURIBaseIDs.takeIf { it.isNotEmpty() },
            results = mergedResults,
            tool = baseRun.tool.copy(
                driver = baseRun.tool.driver.copy(
                    rules = mergedRules
                )
            )
        )
    }.values.filterNotNull().toList()
}

private fun mergeOriginalURIBaseIDs(runs: List<Run>): Pair<Map<String, ArtifactLocation>, List<Map<String, String>>> {
    if (runs.isEmpty()) return emptyMap<String, ArtifactLocation>() to emptyList()

    val mergedMap = mutableMapOf<String, ArtifactLocation>()
    var keyCounter = 1

    val runKeyMappings = runs.map { run ->
        run.originalURIBaseIDS?.mapValues { (key, artifactLocation) ->
            when (mergedMap[key]) {
                null -> {
                    mergedMap[key] = artifactLocation
                    key
                }
                artifactLocation -> key
                else -> {
                    val newKey = generateSequence("${key}_${keyCounter}") { "${key}_${++keyCounter}" }
                        .first { !mergedMap.containsKey(it) }
                    mergedMap[newKey] = artifactLocation
                    newKey
                }
            }
        }.orEmpty()
    }

    return mergedMap to runKeyMappings
}

private fun Result.updateArtifactLocationBaseIds(keyMappings: Map<String, String>): Result {
    if (keyMappings.isEmpty()) return this

    val updatedLocations = locations?.map { location ->
        val updatedPhysicalLocation = location.physicalLocation?.let { physicalLocation ->
            val updatedArtifactLocation = physicalLocation.artifactLocation?.let { artifactLocation ->
                val uriBaseId = artifactLocation.uriBaseID
                if (uriBaseId != null && keyMappings.containsKey(uriBaseId) && keyMappings[uriBaseId] != uriBaseId) {
                    artifactLocation.copy(uriBaseID = keyMappings[uriBaseId])
                } else {
                    artifactLocation
                }
            }
            physicalLocation.copy(artifactLocation = updatedArtifactLocation)
        }
        location.copy(physicalLocation = updatedPhysicalLocation)
    }

    return copy(locations = updatedLocations)
}
