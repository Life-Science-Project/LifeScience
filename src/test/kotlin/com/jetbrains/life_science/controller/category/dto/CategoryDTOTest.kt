package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.util.categoryNameRegex
import com.jetbrains.life_science.util.regex.assertMatches
import com.jetbrains.life_science.util.regex.assertNotMatches
import org.junit.jupiter.api.Test

internal class CategoryDTOTest {

    @Test
    fun `category name regex validation`() {
        val regex = categoryNameRegex.toRegex()
        regex.assertMatches(
            "my category",
            "category",
            "three words name",
            "three words name and three more"
        )
        regex.assertNotMatches(
            "too many letters to match this name".repeat(10),
            "   starts with spaces",
            "ends with spaces   ",
            "     "
        )
    }
}
