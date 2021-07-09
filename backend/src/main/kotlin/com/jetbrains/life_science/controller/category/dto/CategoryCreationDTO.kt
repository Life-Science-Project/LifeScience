package com.jetbrains.life_science.controller.category.dto

data class CategoryCreationDTO(
    val name: String,
    val initialParentId: Long? = null
)