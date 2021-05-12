package com.jetbrains.life_science.article.version.view

import com.jetbrains.life_science.article.master.view.ArticleFullPageView
import com.jetbrains.life_science.article.section.view.SectionLazyViewMapper
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import org.springframework.stereotype.Component

@Component
class ArticleVersionViewMapper(val sectionLazyMapper: SectionLazyViewMapper) {

    fun toViews(articleVersions: List<ArticleVersion>): List<ArticleVersionView> {
        return articleVersions.map { toView(it) }
    }

    fun toView(version: ArticleVersion): ArticleVersionView {

        return ArticleVersionView(
            id = version.id,
            name = version.name,
            articleId = version.mainArticle.id,
            sections = sectionLazyMapper.createViews(
                version.sections.filter { it.visible }
            ),
            state = version.state
        )
    }

    fun toCompletedView(articleVersion: ArticleVersion): ArticleFullPageView {
        require(articleVersion.state == State.PUBLISHED_AS_ARTICLE) { "Version is not published yet" }
        return ArticleFullPageView(
            articleId = articleVersion.articleId,
            articleName = articleVersion.name,
            sections = sectionLazyMapper.createViews(articleVersion.sections),
            articleVersionId = articleVersion.id
        )
    }

    fun toCompletedView(protocolVersion: ArticleVersion, articleVersion: ArticleVersion): ArticleFullPageView {
        require(articleVersion.state == State.PUBLISHED_AS_ARTICLE) { "Version is not published yet" }
        require(protocolVersion.state == State.PUBLISHED_AS_PROTOCOL) { "Version is not published yet" }

        val sections = sectionLazyMapper.createViews(articleVersion.sections) +
            sectionLazyMapper.createViews(protocolVersion.sections)
        return ArticleFullPageView(
            articleId = articleVersion.articleId,
            articleName = articleVersion.name,
            sections = sections,
            articleVersionId = articleVersion.id,
            protocolId = protocolVersion.id,
            protocolName = articleVersion.name
        )
    }
}
