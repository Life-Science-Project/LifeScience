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
            "six words name, no more allowed"
        )
        regex.assertNotMatches(
            "too many words to match this name",
            "   starts with spaces",
            "ends with spaces   ",
            "     ",
            "veeeeeeeeeeeeery long word"
        )
    }
}
