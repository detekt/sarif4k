package io.github.detekt.sarif4k

import kotlinx.io.Buffer
import kotlinx.io.readString
import kotlinx.io.writeString
import kotlin.test.Test
import kotlin.test.assertEquals

class SarifSerializerStreamingTest {

    private val sarifSchema210 = sampleSarifSchema210()

    @Test
    fun `streaming minified json matches string minified json`() {
        val stringResult = SarifSerializer.toMinifiedJson(sarifSchema210)
        val streamResult = Buffer().also {
            SarifSerializer.toMinifiedJson(sarifSchema210, it)
        }.readString()
        assertEquals(stringResult, streamResult)
    }

    @Test
    fun `streaming json matches string json`() {
        val stringResult = SarifSerializer.toJson(sarifSchema210)
        val streamResult = Buffer().also {
            SarifSerializer.toJson(sarifSchema210, it)
        }.readString()
        assertEquals(stringResult, streamResult)
    }

    @Test
    fun `streaming input can be deserialized back`() {
        val input = Buffer().also {
            it.writeString(SarifSerializer.toMinifiedJson(sarifSchema210))
        }
        val deserialized = SarifSerializer.fromJson(input)
        assertEquals(sarifSchema210, deserialized)
    }

    @Test
    fun `streaming output can be deserialized back`() {
        val buffer = Buffer().also {
            SarifSerializer.toMinifiedJson(sarifSchema210, it)
        }
        val deserialized = SarifSerializer.fromJson(buffer)
        assertEquals(sarifSchema210, deserialized)
    }
}
