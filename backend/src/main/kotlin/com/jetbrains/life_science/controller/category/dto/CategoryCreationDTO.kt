package com.jetbrains.life_science.controller.category.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class CategoryCreationDTO(
    @field:Pattern(
        regexp = "^[a-zA-Z, ]+$",
        message = "Category name must contain only allowed characters"
    )
    @field:NotEmpty(
        message = "Category name must not be empty"
    )
    val name: String,
    val initialParentId: Long? = null
)
