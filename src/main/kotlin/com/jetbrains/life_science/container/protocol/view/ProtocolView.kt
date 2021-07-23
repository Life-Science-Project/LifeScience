package com.jetbrains.life_science.container.protocol.view

import com.jetbrains.life_science.section.view.SectionView

data class ProtocolView(
    val id: Long,
    val name: String,
    val approachId: Long,
    val sections: List<SectionView>,
)
