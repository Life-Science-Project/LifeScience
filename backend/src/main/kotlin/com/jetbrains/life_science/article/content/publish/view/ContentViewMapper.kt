package com.jetbrains.life_science.article.content.publish.view

import com.jetbrains.life_science.article.content.master.entity.Content
import com.jetbrains.life_science.article.content.master.view.ContentView
import org.springframework.stereotype.Component

@Component
class ContentViewMapper {
    fun createView(content: Content): ContentView {
        return ContentView(content.id, content.text, content.references)
    }
}
