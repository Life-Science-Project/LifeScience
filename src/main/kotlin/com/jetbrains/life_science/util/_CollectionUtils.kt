package com.jetbrains.life_science.util

fun <T> Collection<T>.containsUnique(): Boolean {
    return size == distinct().size
}
