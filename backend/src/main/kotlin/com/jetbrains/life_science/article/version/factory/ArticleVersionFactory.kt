package com.jetbrains.life_science.article.version.factory

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionInfo
import org.springframework.stereotype.Component

@Component
class ArticleVersionFactory {

    fun create(info: ArticleVersionInfo, article: Article): ArticleVersion {
        return ArticleVersion(
            id = info.id,
            name = info.name,
            state = State.EDITING,
            mainArticle = article,
            author = info.user
        )
    }

    fun createCopy(articleVersion: ArticleVersion): ArticleVersion {
        return ArticleVersion(
            id = 0,
            name = articleVersion.name,
            state = State.EDITING,
            mainArticle = articleVersion.mainArticle,
            author = articleVersion.author
        )
    }

    fun setParams(articleVersion: ArticleVersion, info: ArticleVersionInfo, article: Article) {
        articleVersion.author = info.user
        articleVersion.mainArticle = article
        articleVersion.name = info.name
    }
}
