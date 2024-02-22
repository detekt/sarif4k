package io.github.detekt.sarif4k

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals

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
    fun incorrectTags() {
        assertThrows<ClassCastException> {
            PropertyBag(mapOf("tags" to listOf(1))).tags
        }
    }

    companion object {
        @JvmStatic
        fun data(): List<Arguments> {
            return listOf(
                mapOf<String, Any?>() to """{}""",
                mapOf("foo" to null) to """{"foo":null}""",
                mapOf("foo" to mapOf("bar" to listOf("1", 2L, false, 3.5))) to """{"foo":{"bar":["1",2,false,3.5]}}""",
                mapOf("tags" to null) to """{"tags":null}""",
                mapOf("tags" to emptyList<Nothing>()) to """{"tags":[]}""",
            ).map { (first, second) -> Arguments.of(first, second) }
        }
    }
}
