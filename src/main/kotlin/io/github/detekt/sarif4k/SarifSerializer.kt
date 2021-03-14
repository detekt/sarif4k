package io.github.detekt.sarif4k

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SarifSerializer {

    fun toMinifiedJson(sarif: SarifSchema210): String = Json.encodeToString(sarif)

    fun toJson(sarif: SarifSchema210): String = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }.encodeToString(sarif)

    fun fromJson(json: String): SarifSchema210 = Json.decodeFromString(json)
}