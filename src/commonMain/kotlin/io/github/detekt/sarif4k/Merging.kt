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

        val mergedResults = runs.flatMap { it.results.orEmpty() }
        val mergedRules = runs.flatMap { it.tool.driver.rules.orEmpty() }.distinctBy { it.id }

        baseRun.copy(
            results = mergedResults,
            tool = baseRun.tool.copy(
                driver = baseRun.tool.driver.copy(
                    rules = mergedRules
                )
            )
        )
    }.values.filterNotNull().toList()
}
