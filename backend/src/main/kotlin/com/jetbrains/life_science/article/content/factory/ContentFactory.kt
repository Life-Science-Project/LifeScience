package com.jetbrains.life_science.article.content.factory

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.content.service.ContentInfo
import org.springframework.stereotype.Component

@Component
class ContentFactory {

    fun create(info: ContentInfo): Content {
        return Content(
            sectionId = info.sectionId,
            text = info.text,
            tags = info.tags,
            references = info.references,
            id = null
        )
    }

    fun copy(origin: Content): Content {
        return Content(
            sectionId = origin.sectionId,
            text = origin.text,
            tags = origin.tags,
            references = origin.references,
            id = null
        )
    }

    fun setParams(origin: Content, info: ContentInfo) {
        origin.text = info.text
        origin.tags = info.tags
        origin.sectionId = info.sectionId
        origin.references = info.references
    }
}
