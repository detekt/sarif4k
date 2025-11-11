package io.github.detekt.sarif4k

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public object SarifSerializer {
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    public fun toMinifiedJson(sarif: SarifSchema210): String = Json.encodeToString(sarif)

    public fun toJson(sarif: SarifSchema210): String = json.encodeToString(sarif) + "\n"

    public fun fromJson(json: String): SarifSchema210 = Json.decodeFromString(json)
}
