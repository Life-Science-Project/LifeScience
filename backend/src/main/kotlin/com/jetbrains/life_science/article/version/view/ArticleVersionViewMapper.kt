package com.jetbrains.life_science.article.version.view

import com.jetbrains.life_science.article.section.view.SectionLazyViewMapper
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class ArticleVersionViewMapper(val sectionLazyMapper: SectionLazyViewMapper) {

    fun createViews(articleVersions: List<ArticleVersion>): List<ArticleVersionView> {
        return articleVersions.map { createView(it) }
    }

    fun createLazyViews(versions: List<ArticleVersion>): List<ArticleVersionLazyView> {
        return versions.map { ArticleVersionLazyView(it.id, it.name) }
    }

    fun createView(articleVersion: ArticleVersion): ArticleVersionView {
        return ArticleVersionView(
            id = articleVersion.id,
            name = articleVersion.name,
            articleId = articleVersion.mainArticle.id,
            sections = sectionLazyMapper.createViews(
                articleVersion.sections.filter { it.visible }
            ),
            state = articleVersion.state
        )
    }
}
