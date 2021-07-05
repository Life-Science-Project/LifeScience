package com.jetbrains.life_science.protocol.view

import com.jetbrains.life_science.section.view.ProtocolSectionView

data class ProtocolView(
    val id: Long,
    val name: String,
    val approachId: Long,
    val sections: List<ProtocolSectionView>,
)
