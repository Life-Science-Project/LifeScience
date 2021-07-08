package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.category.entity.Category

interface ApproachInfo {
    val id: Long

    val name: String

    val categories: List<Category>

    val tags: List<String>
}
