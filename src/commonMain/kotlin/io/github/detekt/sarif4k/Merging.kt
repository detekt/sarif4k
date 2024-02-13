@file:JvmName("Merging")

package io.github.detekt.sarif4k

import kotlinx.serialization.json.JsonArray
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

fun PropertyBag.merge(other: PropertyBag): PropertyBag {
    var merge = this + other
    val aTags = this["tags"] as? JsonArray
    val bTags = other["tags"] as? JsonArray

    if (aTags != null && bTags != null) {
        merge = merge.toMutableMap().also {
            it["tags"] = JsonArray((aTags + bTags).distinct())
        }
    }

    return PropertyBag(merge)
}

fun List<Run>.merge(other: List<Run>): List<Run> {
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
