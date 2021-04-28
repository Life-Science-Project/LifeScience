package com.jetbrains.life_science.article.version.view

import com.jetbrains.life_science.article.section.view.SectionLazyViewMapper
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class ArticleVersionViewMapper(val sectionLazyMapper: SectionLazyViewMapper) {

    fun createViews(articleVersions: List<ArticleVersion>): List<ArticleVersionView> {
        return articleVersions.map { createView(it) }
    }

    fun createView(articleVersion: ArticleVersion): ArticleVersionView {
        return ArticleVersionView(
            name = articleVersion.name,
            articleId = articleVersion.mainArticle.id,
            sections = articleVersion.sections
                .filter { it.visible }
                .map { sectionLazyMapper.createView(it) },
            state = articleVersion.state
        )
    }
}
