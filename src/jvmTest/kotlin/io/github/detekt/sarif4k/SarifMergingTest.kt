package io.github.detekt.sarif4k

import io.github.detekt.sarif4k.util.resourceAsTextContent
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.assertThrows

class SarifMergingTest {
    @Test
    fun `merge output from sarifs of the same tool`() {
        testMerge(
            "output.sarif.json",
            "input_1.sarif.json",
            "input_2.sarif.json"
        )
    }

    @Test
    fun `merge output from sarifs with different tools`() {
        testMerge(
            "output_different_tool.sarif.json",
            "input_1.sarif.json",
            "input_2_different_tool.sarif.json"
        )
    }

    @Test
    fun `merge output from sarifs of the same tool with different uriBaseIds`() {
        testMerge(
            "output_other_uribaseid.sarif.json",
            "input_1.sarif.json",
            "input_1_other_uribaseid.sarif.json"
        )
    }

    @Test
    fun `fail merging by default when the schema does not match`() {
        assertThrows<IllegalArgumentException> {
            testMerge(
                "output.sarif.json",
                "input_1.sarif.json",
                "input_2_different_schema.sarif.json"
            )
        }
    }

    @Test
    fun `fail merging by default when the version does not match`() {
        assertThrows<IllegalArgumentException> {
            testMerge(
                "output.sarif.json",
                "input_1.sarif.json",
                "input_2_different_version.sarif.json"
            )
        }
    }

    @Test
    fun `allow mismatched schema when specified in the config`() {
            testMerge(
                "output.sarif.json",
                "input_1.sarif.json",
                "input_2_different_schema.sarif.json",
                config = MergingConfig(allowMismatchedSchema = true)
            )
    }

    private fun testMerge(expectedOutputFile: String, vararg inputFiles: String, config: MergingConfig = MergingConfig()) {
        val inputs = inputFiles.map {
            SarifSerializer.fromJson(resourceAsTextContent(it))
        }

        val actual = inputs.reduce { schema, other -> schema.merge(other, config) }
        val expected = SarifSerializer.fromJson(resourceAsTextContent(expectedOutputFile))
        assertEquals(expected, actual)
    }
}
