package com.jetbrains.life_science.category.controller

import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.dto.CategoryDTOToInfoAdapter
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.category.view.CategoryView
import com.jetbrains.life_science.category.view.CategoryViewMapper
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val categoryService: CategoryService,
    val mapper: CategoryViewMapper
) {

    @GetMapping("/root")
    fun getRootCategories(): List<CategoryView> {
        val rootCategories = categoryService.getRootCategories()
        return rootCategories.map { mapper.createView(it) }
    }

    @GetMapping("/{categoryId}")
    fun getCategory(@PathVariable categoryId: Long): CategoryView {
        val category = categoryService.getCategory(categoryId)
        return mapper.createView(category)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createCategory(@RequestBody @Valid categoryDTO: CategoryDTO, principal: Principal): CategoryView {
        val category = categoryService.createCategory(
            CategoryDTOToInfoAdapter(categoryDTO)
        )
        return mapper.createView(category)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @RequestBody @Valid categoryDTO: CategoryDTO,
        principal: Principal
    ): CategoryView {
        val category = categoryService.updateCategory(
            CategoryDTOToInfoAdapter(categoryDTO, categoryId)
        )
        return mapper.createView(category)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long, principal: Principal) {
        categoryService.deleteCategory(categoryId)
    }
}
