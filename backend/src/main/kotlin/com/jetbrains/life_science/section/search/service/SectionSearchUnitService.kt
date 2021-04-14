package com.jetbrains.life_science.section.search.service

import com.jetbrains.life_science.section.entity.Section

interface SectionSearchUnitService {

    fun create(section: Section)

    fun delete(id: Long)

    fun update(section: Section)
}
