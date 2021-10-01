package com.jetbrains.life_science.container

import com.jetbrains.life_science.section.entity.Section

interface ContainsSections {
    fun addSection(id: Long, section: Section)

    fun removeSection(id: Long, section: Section)

    fun hasSection(id: Long, section: Section): Boolean
}
