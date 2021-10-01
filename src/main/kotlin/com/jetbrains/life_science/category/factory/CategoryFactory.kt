package com.jetbrains.life_science.category.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryInfo
import com.jetbrains.life_science.util.UTCZone
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CategoryFactory {
    fun createCategory(info: CategoryInfo, parent: Category?): Category {
        val parents = parent?.let { mutableListOf(it) } ?: mutableListOf()
        return Category(
            id = 0,
            name = info.name,
            subCategories = mutableListOf(),
            approaches = mutableListOf(),
            parents = parents,
            creationDate = LocalDateTime.now(UTCZone),
            aliases = info.aliases
        )
    }
}
