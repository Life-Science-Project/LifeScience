package com.jetbrains.life_science.article.content.factory

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.content.service.ContentInfo
import org.springframework.stereotype.Component

@Component
class ContentFactory {

    fun create(info: ContentInfo): Content {
        return Content(info.sectionId, info.text, info.tags, info.references, null)
    }

    fun copy(origin: Content): Content {
        return Content(origin.sectionId, origin.text, origin.tags, origin.references, null)
    }

    fun setParams(origin: Content, info: ContentInfo) {
        origin.text = info.text
        origin.tags = info.tags
        origin.sectionId = info.sectionId
        origin.references = info.references
    }
}
