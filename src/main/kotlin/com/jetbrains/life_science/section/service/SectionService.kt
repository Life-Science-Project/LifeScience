package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section

interface SectionService {

    fun create(info: SectionCreationInfo): Section

    fun createMany(infos: List<SectionCreationInfo>, existingSections: List<Section>): List<Section>

    fun deleteById(id: Long, allSections: List<Section>)

    fun getById(id: Long): Section

    fun existsById(id: Long): Boolean

    fun update(section: Section, info: SectionInfo): Section

    fun publish(sectionId: Long)

    fun archive(sectionId: Long)
}
