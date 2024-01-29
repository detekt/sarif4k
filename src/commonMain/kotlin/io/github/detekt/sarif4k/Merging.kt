package io.github.detekt.sarif4k

fun SarifSchema210.merge(other: SarifSchema210): SarifSchema210 {
    require(schema == other.schema) { "Cannot merge sarifs with different schemas: '$schema' or '${other.schema}'" }
    require(version == other.version) { "Cannot merge sarifs with different versions: '$version' or '${other.version}'" }

    val mergedExternalProperties = (inlineExternalProperties.orEmpty() + other.inlineExternalProperties.orEmpty())
        .takeIf { it.isNotEmpty() }

    val mergedProperties = properties.merge(other.properties)

    val mergedRuns = runs.merge(other.runs)

    return SarifSchema210(
        schema,
        version,
        mergedExternalProperties,
        mergedProperties,
        mergedRuns
    )
}

fun PropertyBag?.merge(other: PropertyBag?): PropertyBag? {
    return (this?.tags.orEmpty() + other?.tags.orEmpty())
        .takeIf { it.isNotEmpty() }
        ?.let { PropertyBag(it) }
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
