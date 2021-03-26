package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.exceptions.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.entity.SectionInfo
import com.jetbrains.life_science.section.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SectionServiceImpl @Autowired constructor(
    val sectionRepository: SectionRepository,
) : SectionService {

    override fun addSection(sectionInfo: SectionInfo) {
        val parent = sectionInfo.getParentID()?.let { sectionRepository.getOne(it) }
        sectionRepository.save(Section(sectionInfo.getID(), sectionInfo.getName(), parent))
    }

    override fun deleteSection(id: Long) {
        if (!sectionRepository.existsById(id)) {
            throw SectionNotFoundException()
        }
        sectionRepository.deleteById(id)
    }

    override fun getSection(id: Long): Section {
        if (!sectionRepository.existsById(id)) {
            throw SectionNotFoundException()
        }
        return sectionRepository.getOne(id)
    }
}