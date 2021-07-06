package com.jetbrains.life_science.content.publish.service

import com.jetbrains.life_science.content.publish.entity.Content

interface ContentService {

    fun delete(id: String)

    fun deleteBySectionId(sectionId: Long)

    fun findBySectionId(sectionId: Long): Content?

    fun findById(contentId: String?): Content

    fun publishBySectionId(sectionId: Long)
}
