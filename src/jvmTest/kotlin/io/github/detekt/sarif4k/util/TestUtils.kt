package io.github.detekt.sarif4k.util

import java.io.InputStream
import java.io.InputStreamReader

internal object Resources

internal fun resourceStream(name: String): InputStream {
    val explicitName = if (name.startsWith("/")) name else "/$name"
    val resource = Resources::class.java.getResourceAsStream(explicitName)
    requireNotNull(resource) { "Make sure the resource '$name' exists!" }
    return resource
}

internal fun resourceAsTextContent(name: String) = InputStreamReader(resourceStream(name)).use { it.readText() }
