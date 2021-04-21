package com.jetbrains.life_science.article.version.view

import com.jetbrains.life_science.article.section.view.SectionLazyView

class ArticleVersionView(
    val name: String,
    val articleId: Long,
    val sections: List<SectionLazyView>
)
