package com.jetbrains.life_science.article.version.search.factory

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.search.ArticleVersionSearchUnit
import org.springframework.stereotype.Component

@Component
class ArticleSearchUnitFactory {

    fun create(articleVersion: ArticleVersion): ArticleVersionSearchUnit {
        return ArticleVersionSearchUnit(articleVersion.id, articleVersion.articleId, articleVersion.name)
    }
}
