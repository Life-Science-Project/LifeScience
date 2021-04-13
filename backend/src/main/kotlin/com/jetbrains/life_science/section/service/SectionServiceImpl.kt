package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.exception.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.entity.SectionInfo
import com.jetbrains.life_science.section.factory.SectionFactory
import com.jetbrains.life_science.section.repository.SectionRepository
import org.springframework.stereotype.Service

@Service
class SectionServiceImpl(
    val sectionRepository: SectionRepository,
    val sectionFactory: SectionFactory
) : SectionService {

    override fun addSection(sectionInfo: SectionInfo): Section {
        val parent = sectionInfo.getParentID()?.let {
            sectionRepository.getOne(it)
        }
        return sectionRepository.save(sectionFactory.createSection(sectionInfo, parent))
    }

    override fun deleteSection(id: Long) {
        existByID(id)
        sectionRepository.deleteById(id)
    }

    override fun getSection(id: Long): Section {
        existByID(id)
        return sectionRepository.getOne(id)
    }

    override fun getChildren(id: Long): List<Section> {
        existByID(id)
        return sectionRepository.findAllByParentId(id)
    }

    private fun existByID(id: Long) {
        if (!sectionRepository.existsById(id)) {
            throw SectionNotFoundException("Check if the id is correct: $id")
        }
    }
}
