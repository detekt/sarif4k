package io.github.detekt.sarif4k

import org.junit.jupiter.api.Test
import java.io.Reader
import kotlin.test.assertEquals

class SarifSerializerTest {

    private val sarifSchema210 = sampleSarifSchema210()

    @Test
    fun `serialize kotlin data class to minified json`() {
        val actual = SarifSerializer.toMinifiedJson(sarifSchema210)
        val expected = getResource("/test.sarif.json").readText()
        assertEquals(expected.replace("\\s".toRegex(), ""), actual)
    }

    @Test
    fun `serialize kotlin data class to json`() {
        val actual = SarifSerializer.toJson(sarifSchema210)
        val expected = getResource("/test.sarif.json").readText()
        assertEquals(expected, actual)
    }

    @Test
    fun `deserialize json into kotlin data class`() {
        val actual = SarifSerializer.fromJson(getResource("/test.sarif.json").readText())
        assertEquals(sarifSchema210, actual)
    }
}

private fun getResource(path: String): Reader =
    SarifSerializerTest::class.java.getResourceAsStream(path)!!.reader()
