package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.factory.CategoryFactory
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.exception.category.CategoryNoParentsException
import com.jetbrains.life_science.exception.category.CategoryNotEmptyException
import com.jetbrains.life_science.exception.category.CategoryNotFoundException
import com.jetbrains.life_science.exception.category.CategoryParentNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryServiceImpl(
    val categoryRepository: CategoryRepository,
    val categoryFactory: CategoryFactory,
    val searchService: CategorySearchUnitService
) : CategoryService {

    @Transactional
    override fun createCategory(categoryInfo: CategoryInfo): Category {
        val parent = categoryInfo.parentId?.let { getCategoryParent(it) }
        val createdCategory = categoryFactory.createCategory(categoryInfo, parent)
        val savedCategory = categoryRepository.save(createdCategory)
        searchService.createSearchUnit(savedCategory)
        return savedCategory
    }

    @Transactional
    override fun updateCategory(categoryInfo: CategoryUpdateInfo): Category {
        val category = getById(categoryInfo.id)
        updateParents(categoryInfo, category)
        category.name = categoryInfo.name
        category.aliases = categoryInfo.aliases
        searchService.update(category)
        return category
    }

    private fun updateParents(categoryInfo: CategoryUpdateInfo, category: Category) {
        if (!category.isEmpty) {
            throw CategoryNotEmptyException(category.id)
        }
        deleteParents(category, categoryInfo.parentsToDeleteIds)
        addParents(category, categoryInfo.parentsToAddIds)
        if (category.parents.isEmpty()) {
            throw CategoryNoParentsException()
        }
    }

    private fun addParents(category: Category, idsToAdd: List<Long>) {
        idsToAdd.distinct().map { parentIdToAdd ->
            if (!category.hasParent(parentIdToAdd)) {
                category.parents.add(getCategoryParent(parentIdToAdd))
            }
        }
    }

    private fun deleteParents(category: Category, idsToDelete: List<Long>) {
        idsToDelete.distinct().forEach { parentIdToDelete ->
            val removed = category.parents.removeIf { parent -> parent.id == parentIdToDelete }
            if (!removed) {
                throw CategoryParentNotFoundException(parentIdToDelete)
            }
        }
    }

    @Transactional
    override fun deleteCategory(id: Long) {
        val category = getById(id)
        if (!category.isEmpty) {
            throw CategoryNotEmptyException(id)
        }
        categoryRepository.deleteById(id)
        searchService.deleteSearchUnitById(id)
    }

    private fun getCategoryParent(parentId: Long): Category {
        return getCategorySafe(parentId) ?: throw CategoryParentNotFoundException(parentId)
    }

    private fun getCategorySafe(id: Long): Category? {
        return categoryRepository.findCategoryById(id)
    }

    override fun getById(id: Long): Category {
        return getCategorySafe(id) ?: throw CategoryNotFoundException(id)
    }

    override fun getRootCategories(): List<Category> {
        return categoryRepository.findCategoriesByParentsEmpty()
    }
}
