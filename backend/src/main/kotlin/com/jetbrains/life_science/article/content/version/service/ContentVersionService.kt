package com.jetbrains.life_science.article.content.version.service

import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.service.ContentInfo

interface ContentVersionService {

    fun create(contentInfo: ContentInfo): Content

    fun findById(contentId: String?): Content

    fun update(contentInfo: ContentInfo): Content

    fun delete(contentId: String)

    fun findBySectionId(sectionId: Long): Content?

    fun deleteBySectionId(sectionId: Long)
}
