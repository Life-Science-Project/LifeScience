package com.jetbrains.life_science.controller.searchUnitGenerator

import com.jetbrains.life_science.util.category_search_unit_generator.CategorySearchUnitGenerator
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/generator")
class SearchUnitGeneratorController(
    val categorySearchUnitGenerator: CategorySearchUnitGenerator
) {

    @PatchMapping("/category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun generateCategoriesSearchUnits() {
        categorySearchUnitGenerator.generate()
    }
}
