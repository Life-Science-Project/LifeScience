package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.publish.view.ContentView
import com.jetbrains.life_science.article.section.parameter.view.ParameterView

data class SectionView(
    val id: Long,
    val articleVersionId: Long,
    val name: String,
    val description: String?,
    val contents: ContentView?,
    val parameters: List<ParameterView>,
    val order: Int,
    val visible: Boolean
)
