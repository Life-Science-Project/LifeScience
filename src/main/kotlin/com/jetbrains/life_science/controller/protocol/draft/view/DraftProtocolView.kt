package com.jetbrains.life_science.controller.protocol.draft.view

import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.section.view.SectionShortView
import com.jetbrains.life_science.controller.user.view.UserShortView

data class DraftProtocolView(
    val id: Long,
    val name: String,
    val approach: ApproachShortView,
    val sections: List<SectionShortView>,
    val participants: List<UserShortView?>
)
