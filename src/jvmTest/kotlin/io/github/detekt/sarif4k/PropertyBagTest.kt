package io.github.detekt.sarif4k

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class PropertyBagTest {

    @ParameterizedTest
    @MethodSource("data")
    fun encode(propertyBag: PropertyBag, json: String) {
        assertEquals(json, Json.encodeToString(propertyBag))
    }

    @ParameterizedTest
    @MethodSource("data")
    fun decode(propertyBag: PropertyBag, json: String) {
        assertEquals(propertyBag, Json.decodeFromString<PropertyBag>(json))
    }

    companion object {
        @JvmStatic
        fun data(): List<Arguments> {
            return listOf(
                PropertyBag("foo" to null) to """{"foo":null}""",
                PropertyBag("tags" to null) to """{"tags":null}""",
            ).map { (first, second) -> Arguments.of(first, second) }
        }
    }
}

private fun PropertyBag(vararg values: Pair<String, Any?>) = PropertyBag(mapOf(*values))
