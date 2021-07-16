package com.jetbrains.life_science.validator

import com.jetbrains.life_science.exception.validator.ValidationFailedException

inline fun <reified T : Enum<T>> validateEnumValue(
    name: String,
    lazyMessage: (() -> String)
) {
    try {
        enumValueOf<T>(name)
    } catch (e: IllegalArgumentException) {
        throw ValidationFailedException(lazyMessage())
    }
}
