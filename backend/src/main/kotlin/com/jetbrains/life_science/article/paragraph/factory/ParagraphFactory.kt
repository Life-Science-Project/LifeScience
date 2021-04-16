package com.jetbrains.life_science.article.paragraph.factory

import com.jetbrains.life_science.article.paragraph.entity.Paragraph
import com.jetbrains.life_science.article.paragraph.service.ParagraphInfo
import org.springframework.stereotype.Component

@Component
class ParagraphFactory {

    fun create(info: ParagraphInfo): Paragraph {
        return Paragraph(info.sectionId, info.text, info.tags, info.references, null)
    }

    fun copy(origin: Paragraph): Paragraph {
        return Paragraph(origin.sectionId, origin.text, origin.tags, origin.references, null)
    }

    fun setParams(origin: Paragraph, info: ParagraphInfo) {
        origin.text = info.text
        origin.tags = info.tags
        origin.sectionId = info.sectionId
        origin.references = info.references
    }
}
