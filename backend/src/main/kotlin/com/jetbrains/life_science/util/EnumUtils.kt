package com.jetbrains.life_science.util

/**
 * @author Потапов Александр
 * @since 07.05.2021
 */
inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? =
    T::class.java.enumConstants.firstOrNull { it.name == name }
