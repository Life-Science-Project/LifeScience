package com.jetbrains.life_science.container.approach.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface ApproachInfo {
    val id: Long

    val name: String

    val aliases: List<String>

    val categories: List<Category>

    val tags: List<String>

    val owner: Credentials
}
