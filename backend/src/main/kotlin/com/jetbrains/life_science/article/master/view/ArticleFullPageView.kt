package com.jetbrains.life_science.article.master.view

import com.jetbrains.life_science.article.section.view.SectionLazyView

// TODO: refactor this view to two inner views
data class ArticleFullPageView(
    val articleName: String,
    val articleId: Long,
    val sections: List<SectionLazyView>,
    val protocolId: Long? = null,
    val protocolName: String? = null
)
