package com.jetbrains.life_science.article.content.factory

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.content.service.ContentInfo
import org.springframework.stereotype.Component

@Component
class ContentFactory {

    fun create(info: ContentInfo, contentId: String? = null): Content {
        return Content(info.sectionId, info.text, info.tags, info.references, contentId)
    }

    fun copy(origin: Content): Content {
        return Content(origin.sectionId, origin.text, origin.tags, origin.references, null)
    }
}
