package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.util.categoryNameRegex
import javax.validation.constraints.Pattern

data class CategoryAliasDTO(
    @field:Pattern(
        regexp = categoryNameRegex,
        message = "Category alias must contain only allowed characters"
    )
    val alias: String
)
