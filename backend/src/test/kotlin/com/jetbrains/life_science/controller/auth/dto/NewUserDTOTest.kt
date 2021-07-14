package com.jetbrains.life_science.controller.auth.dto

import com.jetbrains.life_science.util.regex.assertMatches
import com.jetbrains.life_science.util.regex.assertNotMatches
import org.junit.jupiter.api.Test

internal class NewUserDTOTest {

    @Test
    fun `test name regex validation`() {
        val nameRegex = nameRegexp.toRegex()
        assertNotMatchesDigits(nameRegex)
        nameRegex.assertNotMatches(
            "a",
            "_Abc",
            "A+BC",
            "Alex".repeat(30)
        )
        nameRegex.assertMatches(
            "Alex",
            "Ben",
            "Swan"
        )
    }

    private fun assertNotMatchesDigits(pattern: Regex) {
        pattern.assertNotMatches(
            *(0 until 12)
                .map { (1 until it).joinToString("") }
                .toTypedArray()
        )
    }
}
