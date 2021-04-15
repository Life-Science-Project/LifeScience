package com.jetbrains.life_science.category.controller

import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.dto.CategoryDTOToInfoAdapter
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.category.view.CategoryView
import com.jetbrains.life_science.category.view.CategoryViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val service: CategoryService,
    val mapper: CategoryViewMapper
) {

    @GetMapping
    fun getCategories(): List<CategoryView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{categoryId}")
    fun getCategory(@PathVariable categoryId: Long): CategoryView {
        val category = service.getCategory(categoryId)
        val children = service.getChildren(categoryId)
        // TODO(#54): add articles to category view
        return mapper.createView(category, children)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createCategory(@RequestBody @Valid categoryDTO: CategoryDTO, principal: Principal): CategoryView {
        service.createCategory(CategoryDTOToInfoAdapter(categoryDTO))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateCategory(@RequestBody @Valid categoryDTO: CategoryDTO, principal: Principal): CategoryView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long, principal: Principal): ResponseEntity<Void> {
        // TODO(#54): implement method
        // return ResponseEntity.ok().build()
        throw UnsupportedOperationException("Not yet implemented")
    }
}
