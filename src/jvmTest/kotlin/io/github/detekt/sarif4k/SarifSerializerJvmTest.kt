package io.github.detekt.sarif4k

import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class SarifSerializerJvmTest {

    private val sarifSchema210 = sampleSarifSchema210()

    @Test
    fun `output stream adapter matches string minified json`() {
        val stringResult = SarifSerializer.toMinifiedJson(sarifSchema210)
        val streamResult = ByteArrayOutputStream().also {
            SarifSerializer.toMinifiedJson(sarifSchema210, it)
        }.toString(Charsets.UTF_8.name())
        assertEquals(stringResult, streamResult)
    }

    @Test
    fun `output stream adapter matches string json`() {
        val stringResult = SarifSerializer.toJson(sarifSchema210)
        val streamResult = ByteArrayOutputStream().also {
            SarifSerializer.toJson(sarifSchema210, it)
        }.toString(Charsets.UTF_8.name())
        assertEquals(stringResult, streamResult)
    }

    @Test
    fun `input stream adapter can deserialize back`() {
        val input = ByteArrayInputStream(SarifSerializer.toMinifiedJson(sarifSchema210).toByteArray(Charsets.UTF_8))
        val deserialized = SarifSerializer.fromJson(input)
        assertEquals(sarifSchema210, deserialized)
    }
}
