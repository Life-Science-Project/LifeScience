package com.jetbrains.life_science.content.factory

import com.jetbrains.life_science.content.entity.Content
import com.jetbrains.life_science.content.service.ContentInfo
import org.springframework.stereotype.Component

@Component
class ContentFactory {

    fun create(info: ContentInfo): Content {
        return Content(info.sectionId, info.text, info.tags, info.references)
    }

    fun copy(origin: Content): Content {
        return Content(origin.sectionId, origin.text, origin.tags, origin.references)
    }
}
