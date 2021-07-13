package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.util.categoryNameRegex
import com.jetbrains.life_science.util.regex.assertMatches
import com.jetbrains.life_science.util.regex.assertNotMatches
import org.junit.jupiter.api.Test

internal class CategoryCreationDTOTest {

    @Test
    fun `category name regex validation`() {
        val regex = categoryNameRegex.toRegex()
        regex.assertMatches(
            "my category",
            "category",
            "three words name"
        )
        regex.assertNotMatches(
            "123 qwack",
            "12 _",
            "2123231",
            "_data",
            "+-=sample"
        )
    }
}
