package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.util.categoryNameRegex
import javax.validation.constraints.Pattern

data class CategoryCreationDTO(
    @field:Pattern(
        regexp = categoryNameRegex,
        message = "Category name must contain only allowed characters"
    )
    val name: String,
    val aliases: List<
        @Pattern(
            regexp = categoryNameRegex,
            message = "Category alias must contain only allowed characters"
        )
        String>,
    val initialParentId: Long? = null
)
