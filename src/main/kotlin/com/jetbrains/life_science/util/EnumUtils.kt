package com.jetbrains.life_science.util

import java.lang.IllegalArgumentException

inline fun <reified T : Enum<T>> enumValue(name: String): T? {
    return try {
        enumValueOf<T>(name)
    } catch (e: IllegalArgumentException) {
        null
    }
}
