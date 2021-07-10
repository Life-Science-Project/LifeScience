package com.jetbrains.life_science.controller.category

import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.category.view.CategoryViewMapper
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val categoryService: CategoryService,
    val viewMapper: CategoryViewMapper
) {

    @GetMapping("/root")
    fun getRootCategories(): List<CategoryShortView> {
        val categories = categoryService.getRootCategories()
        return viewMapper.toViewsShort(categories)
    }
}
