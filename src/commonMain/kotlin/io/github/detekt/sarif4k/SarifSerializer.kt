package io.github.detekt.sarif4k

import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.io.decodeFromSource
import kotlinx.serialization.json.io.encodeToSink

public object SarifSerializer {
    internal val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    public fun toMinifiedJson(sarif: SarifSchema210): String = Json.encodeToString(sarif)

    public fun toJson(sarif: SarifSchema210): String = json.encodeToString(sarif) + "\n"

    public fun toMinifiedJson(sarif: SarifSchema210, sink: Sink) {
        Json.encodeToSink(sarif, sink)
    }

    public fun toJson(sarif: SarifSchema210, sink: Sink) {
        json.encodeToSink(sarif, sink)
        sink.writeByte('\n'.code.toByte())
    }

    public fun fromJson(json: String): SarifSchema210 = Json.decodeFromString(json)

    public fun fromJson(source: Source): SarifSchema210 = Json.decodeFromSource(source)
}
