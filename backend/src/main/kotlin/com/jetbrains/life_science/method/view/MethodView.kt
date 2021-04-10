package com.jetbrains.life_science.method.view

import com.jetbrains.life_science.container.view.ContainerView

class MethodView(
    val name: String,
    val sectionID: Long?,
    val generalInfo: ContainerView,
    val containersId: List<Long>
)
