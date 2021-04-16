package com.jetbrains.life_science.article.paragraph.view

import com.jetbrains.life_science.article.paragraph.entity.Paragraph
import org.springframework.stereotype.Component

@Component
class ParagraphViewMapper {
    fun createView(paragraph: Paragraph): ParagraphView {
        return ParagraphView(paragraph.text, paragraph.references)
    }
}
