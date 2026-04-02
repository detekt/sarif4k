package io.github.detekt.sarif4k

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.OutputStream

/**
 * JVM-only streaming extensions for [SarifSerializer].
 *
 * These functions write SARIF output directly to an [OutputStream] without
 * materializing the entire JSON document as an in-memory [String], which avoids
 * [OutOfMemoryError] for very large reports.
 */

/**
 * Encodes [sarif] as minified JSON directly to [output].
 *
 * This is the streaming equivalent of [SarifSerializer.toMinifiedJson].
 */
public fun SarifSerializer.toMinifiedJson(sarif: SarifSchema210, output: OutputStream) {
    Json.encodeToStream(sarif, output)
}

/**
 * Encodes [sarif] as pretty-printed JSON directly to [output].
 *
 * This is the streaming equivalent of [SarifSerializer.toJson].
 */
public fun SarifSerializer.toJson(sarif: SarifSchema210, output: OutputStream) {
    json.encodeToStream(sarif, output)
    output.write('\n'.code)
}
