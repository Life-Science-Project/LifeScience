package com.jetbrains.life_science.article.content.publish.factory

import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.service.ContentInfo
import org.springframework.stereotype.Component

@Component
class ContentFactory {

    fun create(info: ContentInfo, articleId: Long): Content {
        return Content(
            versionId = articleId,
            sectionId = info.sectionId,
            text = info.text,
            tags = info.tags.toMutableList(),
            references = info.references.toMutableList(),
            id = null
        )
    }

    fun copy(origin: Content): Content {
        return Content(
            versionId = origin.versionId,
            sectionId = origin.sectionId,
            text = origin.text,
            tags = origin.tags,
            references = origin.references,
            id = null
        )
    }

    fun setParams(origin: Content, info: ContentInfo, articleId: Long) {
        origin.text = info.text
        origin.tags = info.tags.toMutableList()
        origin.sectionId = info.sectionId
        origin.references = info.references.toMutableList()
        origin.versionId = articleId
    }
}
