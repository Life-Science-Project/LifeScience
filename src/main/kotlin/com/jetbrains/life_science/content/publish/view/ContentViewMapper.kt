package com.jetbrains.life_science.content.publish.view

import com.jetbrains.life_science.content.publish.entity.Content
import org.springframework.stereotype.Component

@Component
class ContentViewMapper {
    fun createView(content: Content): ContentView {
        return ContentView(content.id, content.text, content.references)
    }
}
