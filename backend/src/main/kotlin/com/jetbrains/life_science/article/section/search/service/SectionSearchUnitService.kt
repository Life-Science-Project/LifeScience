package com.jetbrains.life_science.article.section.search.service

import com.jetbrains.life_science.article.section.entity.Section

interface SectionSearchUnitService {

    fun create(section: Section)

    fun delete(id: Long)

    fun update(section: Section)
}
