package com.jetbrains.life_science.article.version.view

import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.entity.State

data class ArticleVersionView(
    val name: String,
    val articleId: Long,
    val sections: List<SectionLazyView>,
    val state: State
)
