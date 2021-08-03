package com.jetbrains.life_science.replicator.serializer.category

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.replicator.enities.CategoryStorageEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class CategoryToStorageEntityMapper(
    private val categoryService: CategoryService
) {

    @Synchronized
    fun getStorageEntities(): List<CategoryStorageEntity> {
        TODO()
    }

    fun visitCategory(category: Category, resultList: MutableList<CategoryStorageEntity>) {
    }

    private fun mapToStorageEntity(category: Category): CategoryStorageEntity {
        return CategoryStorageEntity(
            id = category.id,
            name = category.name,
            parents = category.parents.map { it.id },
            aliases = category.aliases
        )
    }
}
