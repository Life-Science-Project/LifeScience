package com.jetbrains.life_science.approach.view

import com.jetbrains.life_science.category.view.CategoryInApproachView
import com.jetbrains.life_science.section.view.SectionView

data class ApproachView(
    val id: Long,
    val name: String,
    val categories: List<CategoryInApproachView>,
    val sections: List<SectionView>,
)
