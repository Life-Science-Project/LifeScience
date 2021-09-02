package com.jetbrains.life_science.controller.approach.draft.view

import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.section.view.SectionShortView
import com.jetbrains.life_science.controller.user.view.UserShortView

data class DraftApproachView(
    val id: Long,
    val name: String,
    val categories: List<CategoryShortView>,
    val sections: List<SectionShortView>,
    val participants: List<UserShortView?>
)
