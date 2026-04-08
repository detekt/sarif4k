package io.github.detekt.sarif4k

import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered
import java.io.InputStream
import java.io.OutputStream

/**
 * JVM convenience overload for [SarifSerializer.toMinifiedJson] that adapts an [OutputStream]
 * to the common `kotlinx.io` streaming API.
 */
public fun SarifSerializer.toMinifiedJson(sarif: SarifSchema210, output: OutputStream) {
    val sink = output.asSink().buffered()
    toMinifiedJson(sarif, sink)
    sink.flush()
}

/**
 * JVM convenience overload for [SarifSerializer.toJson] that adapts an [OutputStream]
 * to the common `kotlinx.io` streaming API.
 */
public fun SarifSerializer.toJson(sarif: SarifSchema210, output: OutputStream) {
    val sink = output.asSink().buffered()
    toJson(sarif, sink)
    sink.flush()
}

/**
 * JVM convenience overload for [SarifSerializer.fromJson] that adapts an [InputStream]
 * to the common `kotlinx.io` streaming API.
 */
public fun SarifSerializer.fromJson(input: InputStream): SarifSchema210 = fromJson(input.asSource().buffered())
