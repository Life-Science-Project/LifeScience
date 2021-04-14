package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.entity.CategoryInfo
import com.jetbrains.life_science.category.factory.CategoryFactory
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.exception.CategoryNotFoundException
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    val categoryRepository: CategoryRepository,
    val categoryFactory: CategoryFactory
) : CategoryService {

    override fun createCategory(categoryInfo: CategoryInfo): Category {
        val parent = categoryInfo.getParentID()?.let {
            categoryRepository.getOne(it)
        }
        return categoryRepository.save(categoryFactory.createSection(categoryInfo, parent))
    }

    override fun deleteCategory(id: Long) {
        existByID(id)
        categoryRepository.deleteById(id)
    }

    override fun getCategory(id: Long): Category {
        existByID(id)
        return categoryRepository.getOne(id)
    }

    override fun getChildren(id: Long): List<Category> {
        existByID(id)
        return categoryRepository.findAllByParentId(id)
    }

    private fun existByID(id: Long) {
        if (!categoryRepository.existsById(id)) {
            throw CategoryNotFoundException("Category not found with id: $id")
        }
    }
}
