package com.jetbrains.life_science.controller.protocol.view

import com.jetbrains.life_science.controller.approach.view.ApproachShortView

data class ProtocolView(
    val id: Long,
    val name: String,
    val approach: ApproachShortView
)
