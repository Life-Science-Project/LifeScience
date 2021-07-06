package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section

interface SectionService {

    fun create(info: SectionInfo): Section

    fun deleteById(id: Long)

    fun getById(id: Long): Section

    fun checkExistsById(id: Long)

    fun update(info: SectionInfo): Section

    fun publish(newSections: List<Section>)

    fun archive(sections: List<Section>)
}
