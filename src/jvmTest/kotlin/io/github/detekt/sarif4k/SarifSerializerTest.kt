package io.github.detekt.sarif4k

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class SarifSerializerTest {

    val sarifSchema210 = SarifSchema210(
        schema = "https://raw.githubusercontent.com/oasis-tcs/sarif-spec/master/Schemata/sarif-schema-2.1.0.json",
        version = Version.The210,
        runs = listOf(
            Run(
                tool = Tool(
                    driver = ToolComponent(
                        guid = "022ca8c2-f6a2-4c95-b107-bb72c43263f3",
                        name = "detekt",
                        organization = "detekt",
                        fullName = "detekt",
                        version = "1.0.0",
                        semanticVersion = "1.0.0",
                        downloadURI = "https://github.com/detekt/detekt/releases/download/v1.0.0/detekt",
                        informationURI = "https://detekt.github.io/detekt",
                        rules = emptyList(),
                        language = "en"
                    )
                ),
                originalURIBaseIDS = mapOf(
                    "%SRCROOT%" to ArtifactLocation(uri = "file:///Users/tester/detekt/")
                ),
                results = listOf(
                    Result(
                        ruleID = "detekt.TestSmellA.TestSmellA",
                        message = Message(text = "TestMessage"),
                        locations = listOf(
                            Location(
                                physicalLocation = PhysicalLocation(
                                    artifactLocation = ArtifactLocation(
                                        uri = "TestFile.kt",
                                        uriBaseID = "%SRCROOT%"
                                    ),
                                    region = Region(
                                        startLine = 1,
                                        startColumn = 1
                                    )
                                )
                            )
                        )
                    ),
                    Result(
                        ruleID = "detekt.TestSmellB.TestSmellB",
                        message = Message(text = "TestMessage"),
                        locations = listOf(
                            Location(
                                physicalLocation = PhysicalLocation(
                                    artifactLocation = ArtifactLocation(
                                        uri = "TestFile.kt",
                                        uriBaseID = "%SRCROOT%"
                                    ),
                                    region = Region(
                                        startLine = 1,
                                        startColumn = 1
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    )

    @Test
    fun `serialize kotlin data class to minified json`() {
        val actual = SarifSerializer.toMinifiedJson(sarifSchema210)
        val expected = File(SarifSerializerTest::class.java.getResource("/test.sarif.json").path).readText()
        assertEquals(expected.replace("\\s".toRegex(), ""), actual)
    }

    @Test
    fun `serialize kotlin data class to json`() {
        val actual = SarifSerializer.toJson(sarifSchema210)
        val expected = File(SarifSerializerTest::class.java.getResource("/test.sarif.json").path).readText()
        assertEquals(expected, actual)
    }

    @Test
    fun `deserialize json into kotlin data class`() {
        val actual = SarifSerializer.fromJson(
            File(SarifSerializerTest::class.java.getResource("/test.sarif.json").path).readText()
        )
        assertEquals(sarifSchema210, actual)
    }
}