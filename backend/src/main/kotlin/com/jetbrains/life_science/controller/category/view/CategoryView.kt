package com.jetbrains.life_science.controller.category.view

import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import java.time.LocalDateTime

data class CategoryView(
    val name: String,
    val creationDate: LocalDateTime,
    val subCategories: List<CategoryShortView>,
    val approaches: List<ApproachShortView>
)