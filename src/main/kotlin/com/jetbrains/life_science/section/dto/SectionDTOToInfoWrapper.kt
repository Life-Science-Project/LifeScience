package com.jetbrains.life_science.section.dto

import com.jetbrains.life_science.section.entity.SectionInfo

class SectionDTOToInfoWrapper (private val sectionDTO: SectionDTO) : SectionInfo {

    override fun getID(): Long {
        return 0
    }

    override fun getName(): String {
        return sectionDTO.name
    }

    override fun getParentID(): Long? {
        return sectionDTO.parentID
    }
}