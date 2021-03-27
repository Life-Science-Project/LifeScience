package com.jetbrains.life_science.method.view

import com.jetbrains.life_science.article.view.ArticleViewMapper
import com.jetbrains.life_science.method.entity.Method
import org.springframework.stereotype.Component

@Component
class MethodViewMapper (val articleViewMapper: ArticleViewMapper) {
    fun createView(method: Method): MethodView {
        return MethodView(method.name, method.section.id, articleViewMapper.createView(method.article))
    }
}