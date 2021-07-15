package com.jetbrains.life_science.section.view

import com.jetbrains.life_science.content.publish.view.ContentView

data class SectionView(
    val id: Long,
    val name: String,
    val contents: ContentView?,
    val order: Long,
    val visible: Boolean
)
