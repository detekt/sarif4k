package io.github.detekt.sarif4k

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object SarifSerializer {
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    fun toMinifiedJson(sarif: SarifSchema210): String = Json.encodeToString(sarif)

    fun toJson(sarif: SarifSchema210): String = json.encodeToString(sarif)

    fun fromJson(json: String): SarifSchema210 = Json.decodeFromString(json)
}

// Extracted from https://github.com/Kotlin/kotlinx.serialization/issues/296#issuecomment-1859572541
private fun Any?.toJsonElement(): JsonElement = when (this) {
    null -> JsonNull
    is JsonElement -> this
    is Map<*, *> -> toJsonElement()
    is Collection<*> -> toJsonElement()
    is ByteArray -> this.toList().toJsonElement()
    is CharArray -> this.toList().toJsonElement()
    is ShortArray -> this.toList().toJsonElement()
    is IntArray -> this.toList().toJsonElement()
    is LongArray -> this.toList().toJsonElement()
    is FloatArray -> this.toList().toJsonElement()
    is DoubleArray -> this.toList().toJsonElement()
    is BooleanArray -> this.toList().toJsonElement()
    is Array<*> -> toJsonElement()
    is Boolean -> JsonPrimitive(this)
    is Number -> JsonPrimitive(this)
    is String -> JsonPrimitive(this)
    is Enum<*> -> JsonPrimitive(this.toString())
    else -> error("Can't serialize unknown type: $this")
}

internal fun Map<*, *>.toJsonElement(): JsonObject {
    return JsonObject(this.map { (key, value) -> key as String to value.toJsonElement() }.toMap())
}

private fun Collection<*>.toJsonElement(): JsonArray {
    return JsonArray(this.map { it.toJsonElement() })
}

private fun Array<*>.toJsonElement(): JsonArray {
    return JsonArray(this.map { it.toJsonElement() })
}

private fun JsonElement.toMap(): Any? {
    return when (this) {
        JsonNull -> null
        is JsonArray -> this.map { it.toMap() }
        is JsonObject -> this.toMap()
        is JsonPrimitive -> if (isString) content else {
            content
                .toBooleanStrictOrNull()
                ?: content.toDoubleOrNull()
                ?: content.toLongOrNull()
            ?: error("Unknown primitive type")
        }
    }
}

internal fun JsonObject.toMap(): Map<String, Any?> {
    return this.map { (key, value) -> key to value.toMap() }.toMap()
}
