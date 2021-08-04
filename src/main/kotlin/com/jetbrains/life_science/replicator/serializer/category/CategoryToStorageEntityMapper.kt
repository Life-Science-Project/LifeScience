package com.jetbrains.life_science.replicator.serializer.category

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.replicator.enities.CategoryStorageEntity
import org.springframework.stereotype.Component

@Component
class CategoryToStorageEntityMapper(
    private val categoryService: CategoryService,
) {

    @Synchronized
    fun getStorageEntities(): CategoriesIdsMapWrapper {
        val result = mutableListOf<CategoryStorageEntity>()
        val visited = mutableSetOf<Long>()
        categoryService.getRootCategories().forEach { visitCategory(it, result, visited) }
        val idsMap = postProcessIds(result)
        return CategoriesIdsMapWrapper(result, idsMap)
    }

    private fun postProcessIds(result: MutableList<CategoryStorageEntity>): MutableMap<Long, Long> {
        result.reverse()
        val idsMap = mutableMapOf<Long, Long>()
        var incrementor = 0L
        result.forEach { category ->
            idsMap[category.id] = ++incrementor
            category.id = incrementor
            category.parents = category.parents.map { idsMap[it]!! }
        }
        return idsMap
    }

    private fun visitCategory(
        category: Category,
        result: MutableList<CategoryStorageEntity>,
        visited: MutableSet<Long>
    ) {
        visited.add(category.id)
        category.subCategories.forEach { child ->
            if (child.id !in visited) {
                visitCategory(child, result, visited)
            }
        }
        result.add(mapToStorageEntity(category))
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
