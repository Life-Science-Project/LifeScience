package com.jetbrains.life_science.section.dto

import com.jetbrains.life_science.section.entity.SectionInfo

class SectionDTOToInfoAdapter(private val sectionDTO: SectionDTO) : SectionInfo {

    override fun getID(): Long? {
        return null
    }

    override fun getName(): String {
        return sectionDTO.name
    }

    override fun getParentID(): Long? {
        return sectionDTO.parentID
    }
}
