package io.github.detekt.sarif4k

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SarifSerializer {
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    fun toMinifiedJson(sarif: SarifSchema210): String = Json.encodeToString(sarif)

    fun toJson(sarif: SarifSchema210): String = json.encodeToString(sarif)

    fun fromJson(json: String): SarifSchema210 = Json.decodeFromString(json)
}