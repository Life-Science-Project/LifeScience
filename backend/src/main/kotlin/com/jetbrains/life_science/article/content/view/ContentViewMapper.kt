package com.jetbrains.life_science.article.content.view

import com.jetbrains.life_science.article.content.entity.Content
import org.springframework.stereotype.Component

@Component
class ContentViewMapper {
    fun createView(content: Content): ContentView {
        return ContentView(content.id, content.text, content.references)
    }
}
