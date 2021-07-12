package com.jetbrains.life_science.controller.category.dto

import javax.validation.constraints.NotEmpty

data class CategoryCreationDTO(
    @field:NotEmpty
    val name: String,
    val initialParentId: Long? = null
)
