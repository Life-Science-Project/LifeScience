package com.jetbrains.life_science.section.view

import com.jetbrains.life_science.article.content.publish.view.ContentView

data class ApproachSectionView(
    val id: Long,
    val approachId: Long,
    val name: String,
    val contents: ContentView?,
    val order: Long,
    val visible: Boolean
)
