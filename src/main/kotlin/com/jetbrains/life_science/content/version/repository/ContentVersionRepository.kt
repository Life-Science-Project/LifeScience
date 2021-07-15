package com.jetbrains.life_science.content.version.repository

import com.jetbrains.life_science.content.publish.entity.Content

interface ContentVersionRepository {

    fun save(content: Content): Content

    fun findById(id: String): Content?

    fun deleteById(id: String)

    fun findBySectionId(sectionId: Long): Content?

    fun deleteBySectionId(sectionId: Long)
}
