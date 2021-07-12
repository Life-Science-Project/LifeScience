package com.jetbrains.life_science.controller.category.dto

import javax.validation.constraints.Pattern

data class CategoryUpdateDTO(
    @field:Pattern(
        regexp = "^[a-zA-Z]+$",
        message = "Category name must contain only allowed characters"
    )
    val name: String,
    val parentsToAdd: List<Long>,
    val parentsToDelete: List<Long>,
)
