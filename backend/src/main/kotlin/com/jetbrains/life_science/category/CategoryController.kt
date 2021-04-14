package com.jetbrains.life_science.category

import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.dto.CategoryDTOToInfoAdapter
import com.jetbrains.life_science.category.service.CategoryServiceImpl
import com.jetbrains.life_science.category.view.CategoryView
import com.jetbrains.life_science.category.view.CategoryViewMapper
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val sectionService: CategoryServiceImpl,
    val categoryViewMapper: CategoryViewMapper
) {
    @PostMapping
    fun addSection(@RequestBody @Valid categoryDTO: CategoryDTO) {
        sectionService.createCategory(CategoryDTOToInfoAdapter(categoryDTO))
    }

    @GetMapping("/{id}")
    fun getChildren(@PathVariable id: Long): CategoryView {
        val category = sectionService.getCategory(id)
        val children = sectionService.getChildren(id)
        return categoryViewMapper.createView(category, children)
    }
}
