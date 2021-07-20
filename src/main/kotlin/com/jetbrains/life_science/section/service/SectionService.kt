package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section

interface SectionService {

    fun create(info: SectionInfo): Section

    fun deleteById(id: Long)

    fun getById(id: Long): Section

    fun existsById(id: Long): Boolean

    fun update(info: SectionInfo): Section

    fun publish(sectionId: Long)

    fun archive(sectionId: Long)
}
