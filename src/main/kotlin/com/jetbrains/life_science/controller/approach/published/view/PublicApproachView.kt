package com.jetbrains.life_science.controller.approach.published.view

import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.section.view.SectionShortView
import com.jetbrains.life_science.controller.user.view.UserShortView
import com.jetbrains.life_science.controller.protocol.view.ProtocolShortView

data class PublicApproachView(
    val id: Long,
    val name: String,
    val categories: List<CategoryShortView>,
    val sections: List<SectionShortView>,
    val coAuthors: List<UserShortView?>,
    val protocols: List<ProtocolShortView>
)
