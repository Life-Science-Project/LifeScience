package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.publish.view.ContentView

class SectionView(
    val name: String,
    val description: String?,
    val contents: ContentView?,
    val order: Int,
    val visible: Boolean
)
