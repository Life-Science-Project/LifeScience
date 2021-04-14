package com.jetbrains.life_science.article.version.view

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class ArticleVersionViewMapper {
    fun createView(articleVersion: ArticleVersion): ArticleVersionView {
        return ArticleVersionView(
            articleVersion.name,
            articleVersion.mainArticle.id,
            articleVersion.sections
                .map { it.id }
        )
    }
}
