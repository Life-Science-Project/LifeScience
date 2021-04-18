package com.jetbrains.life_science.article.paragraph.factory

import com.jetbrains.life_science.article.paragraph.entity.Paragraph
import com.jetbrains.life_science.article.paragraph.service.ParagraphInfo
import org.springframework.stereotype.Component

@Component
class ParagraphFactory {

    fun create(info: ParagraphInfo): Paragraph {
        return Paragraph(
            sectionId = info.sectionId,
            text = info.text,
            tags = info.tags,
            references = info.references,
            id = null
        )
    }

    fun copy(origin: Paragraph): Paragraph {
        return Paragraph(
            sectionId = origin.sectionId,
            text = origin.text,
            tags = origin.tags,
            references = origin.references,
            id = null
        )
    }

    fun setParams(origin: Paragraph, info: ParagraphInfo) {
        origin.text = info.text
        origin.tags = info.tags
        origin.sectionId = info.sectionId
        origin.references = info.references
    }
}
