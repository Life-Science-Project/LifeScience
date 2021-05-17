package com.jetbrains.life_science.util

fun <V> Map<*, V>.getOrThrow(key: Any, lazyMessage: () -> String = { "Key $key not found" }): V {
    return get(key) ?: throw IllegalStateException(lazyMessage())
}
