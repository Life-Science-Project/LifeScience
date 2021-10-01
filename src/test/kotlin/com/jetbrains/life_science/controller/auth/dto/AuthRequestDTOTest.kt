package com.jetbrains.life_science.controller.auth.dto

import com.jetbrains.life_science.util.emailRegexLite
import com.jetbrains.life_science.util.passwordRegex
import com.jetbrains.life_science.util.regex.assertMatches
import com.jetbrains.life_science.util.regex.assertNotMatches
import org.junit.jupiter.api.Test

internal class AuthRequestDTOTest {

    @Test
    fun `test password regex validation`() {
        val regex = passwordRegex.toRegex()
        checkPasswordMatches(regex)
        checkPasswordNotMatches(regex)
    }

    private fun checkPasswordNotMatches(passwordRegex: Regex) {
        passwordRegex.assertNotMatches(
            "        ",
            "",
            "1",
            "12",
            "123",
            "1234",
            " 12345",
            "12345 ",
            "ab".repeat(30)
        )
    }

    private fun checkPasswordMatches(passwordRegex: Regex) {
        passwordRegex.assertMatches(
            "123abc",
            "123_abc",
            "1@23_+abc",
            "1@23_+ab24c",
        )
    }

    @Test
    fun `test lite email regex validation`() {
        val regex = emailRegexLite.toRegex()
        regex.assertNotMatches(
            "abc",
            "abc@",
            "abc@gm",
            "abc@@",
        )
        regex.assertMatches(
            "sample@mail.ru",
            "sam.123.ple@gmail.ru",
            "SAM.123.PLE@gmail.ru",
            "abc_def@jetbrains.com"
        )
    }
}
