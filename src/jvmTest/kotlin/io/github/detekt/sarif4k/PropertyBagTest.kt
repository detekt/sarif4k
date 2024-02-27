package io.github.detekt.sarif4k

import kotlinx.serialization.json.Json
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

class PropertyBagTest {

    @ParameterizedTest
    @MethodSource("data")
    fun encode(value: Map<String, Any?>, json: String) {
        assertEquals(json, Json.encodeToString(PropertyBag.serializer(), PropertyBag(value)))
    }

    @ParameterizedTest
    @MethodSource("data")
    fun decode(value: Map<String, Any?>, json: String) {
        assertEquals(PropertyBag(value), Json.decodeFromString(PropertyBag.serializer(), json))
    }

    @Test
    fun noTags() {
        assertEquals(null, PropertyBag(mapOf()).tags)
    }

    @Test
    fun nullTags() {
        assertEquals(null, PropertyBag(mapOf("tags" to null)).tags)
    }

    @Test
    fun emptyTags() {
        assertEquals(emptyList(), PropertyBag(mapOf("tags" to emptyList<Nothing>())).tags)
    }

    @Test
    fun tags() {
        assertEquals(listOf("foo", "bar"), PropertyBag(mapOf("tags" to listOf("foo", "bar"))).tags)
    }

    @Test
    fun incorrectTags_noList() {
        try {
            PropertyBag(mapOf("tags" to 1)).tags
            fail()
        } catch (e: IllegalStateException) {
            assertEquals("Expected a Collection for the value of tags property: 1.", e.message)
            assertNotNull(e.cause)
        }
    }

    @Test
    fun incorrectTags_noString() {
        try {
            PropertyBag(mapOf("tags" to listOf("foo", true, "bar"))).tags
            fail()
        } catch (e: IllegalStateException) {
            assertEquals("Expected a String tag at index 1: true.", e.message)
            assertNotNull(e.cause)
        }
    }

    companion object {
        @JvmStatic
        fun data() = listOf(
            arguments(mapOf<String, Any?>(), """{}"""),
            arguments(mapOf("foo" to null), """{"foo":null}"""),
            arguments(
                mapOf("foo" to mapOf("bar" to listOf("1", 2L, false, 3.5))),
                """{"foo":{"bar":["1",2,false,3.5]}}""",
            ),
            arguments(mapOf("tags" to null), """{"tags":null}"""),
            arguments(mapOf("tags" to emptyList<Nothing>()), """{"tags":[]}"""),
        )
    }
}
