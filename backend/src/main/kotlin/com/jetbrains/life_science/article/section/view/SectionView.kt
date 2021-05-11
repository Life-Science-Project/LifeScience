package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.publish.view.ContentView

data class SectionView(
    val id: Long,
    val articleVersionId: Long,
    val name: String,
    val description: String?,
    val contents: ContentView?,
    val order: Int,
    val visible: Boolean
)
