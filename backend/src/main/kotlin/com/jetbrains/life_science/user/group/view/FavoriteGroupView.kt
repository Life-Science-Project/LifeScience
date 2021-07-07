package com.jetbrains.life_science.user.group.view

import com.jetbrains.life_science.approach.view.PublicApproachInCategoryView
import com.jetbrains.life_science.protocol.view.ProtocolView

data class FavoriteGroupView(
    val id: Long,
    val name: String,
    val subgroups: List<SubgroupView>,
    val protocols: List<ProtocolView>,
    val approaches: List<PublicApproachInCategoryView>
)
