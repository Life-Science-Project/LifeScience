package com.jetbrains.life_science.controller.category.view

import java.time.LocalDateTime

data class CategoryShortView(
    val id: Long,
    val name: String,
    val creationDate: LocalDateTime
)