package com.jetbrains.life_science.content.view

import com.jetbrains.life_science.content.entity.Content
import org.springframework.stereotype.Component

@Component
class ContentViewMapper {
    fun createView(content: Content): ContentView {
        return ContentView(content.text, content.references)
    }
}
