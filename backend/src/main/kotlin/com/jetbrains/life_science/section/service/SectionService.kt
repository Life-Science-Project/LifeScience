package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.entity.SectionInfo

interface SectionService {

    fun addSection(sectionInfo: SectionInfo): Section

    fun deleteSection(id: Long)

    fun getSection(id: Long): Section

    fun getChildren(id: Long): List<Section>
}
