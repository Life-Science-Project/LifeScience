package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.entity.SectionInfo

interface SectionService {

    fun addSection(sectionInfo: SectionInfo)

    fun deleteSection(id: Long)

    fun getSection(id: Long): Section
}