package com.jetbrains.life_science.section.view

class SectionView(
    val id: Long?,
    val parentID: Long?,
    val name: String,
    val children: List<SectionChildrenView>
)
