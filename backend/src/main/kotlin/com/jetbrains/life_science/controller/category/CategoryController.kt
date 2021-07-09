package com.jetbrains.life_science.controller.category

import com.jetbrains.life_science.category.service.CategoryService
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val categoryService: CategoryService
) {

    /**
     * Get root categories test.
     *
     * All root categories expected.
     */
    @Test
    fun `get root categories test`() {

    }



}
