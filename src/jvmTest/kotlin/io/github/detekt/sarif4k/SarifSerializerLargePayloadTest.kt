package io.github.detekt.sarif4k

import kotlinx.io.asSink
import kotlinx.io.buffered
import org.junit.jupiter.api.Test
import java.io.File
import java.io.OutputStream
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class SarifSerializerLargePayloadTest {

    @Test
    fun `streaming handles payload too large for string output`() {
        val stringResult = runHarness("string")
        assertNotEquals(0, stringResult.exitCode, stringResult.output)
        assertContains(stringResult.output, "OutOfMemoryError")

        val streamResult = runHarness("stream")
        assertEquals(0, streamResult.exitCode, streamResult.output)
    }

    private fun runHarness(mode: String): ProcessResult {
        val process = ProcessBuilder(
            javaBinary(),
            "-Xms32m",
            "-Xmx64m",
            "-cp",
            System.getProperty("java.class.path"),
            SarifSerializerLargePayloadHarness::class.java.name,
            mode,
        )
            .redirectErrorStream(true)
            .start()

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val exitCode = process.waitFor()
        return ProcessResult(exitCode, output)
    }

    private fun javaBinary(): String = File(System.getProperty("java.home"), "bin/java").absolutePath
}

private data class ProcessResult(val exitCode: Int, val output: String)

private object SarifSerializerLargePayloadHarness {
    private const val largeMessageSize = 1_000_000
    private const val additionalResults = 128
    private val blackholeOutput = object : OutputStream() {
        override fun write(b: Int) = Unit
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val sarif = sampleSarifSchema210(
            messageText = "x".repeat(largeMessageSize),
            additionalResults = additionalResults,
        )

        when (args.single()) {
            "string" -> SarifSerializer.toMinifiedJson(sarif)
            "stream" -> {
                val sink = blackholeOutput.asSink().buffered()
                SarifSerializer.toMinifiedJson(sarif, sink)
                sink.flush()
            }
            else -> error("Unknown mode: ${args.single()}")
        }
    }
}
