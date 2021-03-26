package com.jetbrains.life_science.method.view

import com.jetbrains.life_science.article.view.ArticleViewMapper
import com.jetbrains.life_science.method.entity.Method
import org.springframework.stereotype.Service

@Service
class MethodViewMapper {
    companion object {
        fun createView(method: Method): MethodView {
            return MethodView(method.name, method.section.id, ArticleViewMapper.createView(method.article))
        }
    }
}