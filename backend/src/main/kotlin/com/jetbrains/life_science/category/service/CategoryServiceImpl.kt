package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.factory.CategoryFactory
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.exception.not_empty.CategoryNotEmptyException
import com.jetbrains.life_science.exception.not_found.CategoryNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryServiceImpl(
    val categoryRepository: CategoryRepository,
    val categoryFactory: CategoryFactory,
    val searchService: CategorySearchUnitService
) : CategoryService {

    override fun createCategory(categoryInfo: CategoryInfo): Category {
        val parent = categoryInfo.parentId?.let {
            existById(it)
            categoryRepository.findById(it).get()
        }
        val saved = categoryRepository.save(categoryFactory.createCategory(categoryInfo, parent))
        searchService.createSearchUnit(saved)
        return saved
    }

    override fun deleteCategory(id: Long) {
        val category = getCategory(id)
        if (category.subCategories.isNotEmpty() || category.articles.isNotEmpty()) {
            throw CategoryNotEmptyException("Category with id $id is not empty")
        }
        categoryRepository.deleteById(id)
        searchService.deleteSearchUnitById(id)
    }

    override fun getCategory(id: Long): Category {
        existById(id)
        return categoryRepository.findById(id).get()
    }

    override fun getCategories(ids: List<Long>): MutableList<Category> {
        return ids.map { getCategory(it) } as MutableList<Category>
    }

    @Transactional
    override fun updateCategory(categoryInfo: CategoryInfo): Category {
        val category = getCategory(categoryInfo.id)
        val parent = categoryInfo.parentId?.let { getCategory(it) }
        categoryFactory.setParams(category, categoryInfo, parent)
        parent?.subCategories?.add(category)
        searchService.update(category)
        return category
    }

    override fun getRootCategories(): List<Category> {
        return categoryRepository.findAllByParentIsNull()
    }

    private fun existById(id: Long) {
        if (!categoryRepository.existsById(id)) {
            throw CategoryNotFoundException("Category not found with id: $id")
        }
    }
}
