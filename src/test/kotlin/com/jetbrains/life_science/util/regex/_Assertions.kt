package com.jetbrains.life_science.util.regex

import org.junit.jupiter.api.Assertions

fun Regex.assertNotMatches(vararg values: String) {
    values.forEach { example ->
        Assertions.assertFalse(this matches example, "regex matches $example")
    }
}

fun Regex.assertMatches(vararg values: String) {
    values.forEach { example ->
        Assertions.assertTrue(this matches example, "regex not matches $example")
    }
}
