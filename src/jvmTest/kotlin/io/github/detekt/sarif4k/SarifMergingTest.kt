package io.github.detekt.sarif4k

import io.github.detekt.sarif4k.util.resourceAsTextContent
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class SarifMergingTest {
    @Test
    fun `merge output from sarifs of the same tool`() {
        testMerge("/output.sarif.json", "/input_1.sarif.json", "/input_2.sarif.json")
    }

    @Test
    fun `merge output from sarifs with different tools`() {
        testMerge(
            "output_different_tool.sarif.json",
            "input_1.sarif.json",
            "input_2_different_tool.sarif.json"
        )
    }

    private fun testMerge(expectedOutputFile: String, vararg inputFiles: String) {
        val inputs = inputFiles.map {
            SarifSerializer.fromJson(resourceAsTextContent(it))
        }

        val actual = SarifSerializer.toMinifiedJson(inputs.reduce(SarifSchema210::merge))
        val expected = resourceAsTextContent(expectedOutputFile)
        assertEquals(expected.replace("\\s".toRegex(), ""), actual)
    }
}
