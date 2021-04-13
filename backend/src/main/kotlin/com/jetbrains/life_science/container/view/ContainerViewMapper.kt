package com.jetbrains.life_science.container.view

import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.article.view.ArticleViewMapper
import com.jetbrains.life_science.container.entity.Container
import org.springframework.stereotype.Component

@Component
class ContainerViewMapper(
    val articleService: ArticleService,
    val articleViewMapper: ArticleViewMapper
) {
    fun createView(container: Container): ContainerView {
        return ContainerView(
            container.name,
            container.description,
            articleService.findAllByContainerId(container.id)
                .map {
                    articleViewMapper.createView(it)
                }
        )
    }
}
