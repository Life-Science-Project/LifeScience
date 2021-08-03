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
        val visited = mutableSetOf<Long>()
        val result = mutableListOf<CategoryStorageEntity>()
        categoryService.getRootCategories().forEach { visitCategory(it, result, visited) }
        return result.reversed()
    }

    fun visitCategory(
        category: Category,
        resultList: MutableList<CategoryStorageEntity>,
        visited: MutableSet<Long>
    ) {
        visited.add(category.id)
        category.subCategories.forEach { child ->
            if (child.id !in visited) {
                visitCategory(child, resultList, visited)
            }
        }
        resultList.add(mapToStorageEntity(category))
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
