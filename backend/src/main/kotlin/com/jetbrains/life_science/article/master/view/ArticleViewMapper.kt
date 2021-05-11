package com.jetbrains.life_science.article.master.view

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import org.springframework.stereotype.Component

@Component
class ArticleViewMapper(
    val versionViewMapper: ArticleVersionViewMapper
) {
    fun createView(article: Article): ArticleView {
        val publishedVersionView = article.versions
            .filter { it.isPublished }
            .map { versionViewMapper.toView(it) }
            .firstOrNull()
        return ArticleView(article.id, publishedVersionView)
    }
}
