package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.factory.CategoryFactory
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.exception.category.CategoryNoParentsException
import com.jetbrains.life_science.exception.category.CategoryNotFoundException
import com.jetbrains.life_science.exception.category.CategoryParentNotFoundException
import com.jetbrains.life_science.exception.not_empty.CategoryNotEmptyException
import org.springframework.stereotype.Service

private const val HEAD_CATEGORY_ID = 0L

@Service
class CategoryServiceImpl(
    val categoryRepository: CategoryRepository,
    val categoryFactory: CategoryFactory,
    val searchService: CategorySearchUnitService
) : CategoryService {


    override fun createCategory(categoryInfo: CategoryInfo): Category {
        val parent = getCategoryParent(categoryInfo.parentId)
        val createdCategory = categoryFactory.createCategory(categoryInfo, parent)
        val savedCategory = categoryRepository.save(createdCategory)
        searchService.createSearchUnit(savedCategory)
        return savedCategory
    }

    override fun updateCategory(categoryInfo: CategoryUpdateInfo): Category {
        val category = getCategory(categoryInfo.id)
        updateParents(categoryInfo, category)
        category.name = categoryInfo.name
        return categoryRepository.save(category)
    }

    private fun updateParents(categoryInfo: CategoryUpdateInfo, category: Category) {
        deleteParents(category, categoryInfo.parentsToDeleteIds)
        addParents(category, categoryInfo.parentsToAddIds)
        if (category.parents.isEmpty()) {
            throw CategoryNoParentsException()
        }
    }

    private fun addParents(category: Category, idsToAdd: List<Long>) {
        val newParents = idsToAdd.map { getCategoryParent(it) }
        category.parents.addAll(newParents)
    }

    private fun deleteParents(category: Category, idsToDelete: List<Long>) {
        idsToDelete.forEach { parentIdToDelete ->
            val removed = category.parents.removeIf { parent -> parent.id == parentIdToDelete }
            if (!removed) {
                throw CategoryParentNotFoundException(parentIdToDelete)
            }
        }
    }

    override fun deleteCategory(id: Long) {
        val category = getCategory(id)
        if (!category.isEmpty) {
            throw IllegalStateException("Category is not empty")
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

    override fun getCategory(id: Long): Category {
        return getCategorySafe(id) ?: throw CategoryNotFoundException(id)
    }


    override fun getRootCategories(): List<Category> {
        return getCategory(HEAD_CATEGORY_ID).subCategories
    }

    private fun existById(id: Long) {
        if (!categoryRepository.existsById(id)) {
            throw CategoryNotFoundException(id)
        }
    }
}
