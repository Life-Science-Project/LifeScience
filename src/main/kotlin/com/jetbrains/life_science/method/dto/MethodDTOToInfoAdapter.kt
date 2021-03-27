package com.jetbrains.life_science.method.dto

import com.jetbrains.life_science.method.entity.MethodInfo

class MethodDTOToInfoAdapter(private val methodDTO: MethodDTO) : MethodInfo {

    override fun getId(): Long? {
        return null
    }

    override fun getName(): String {
        return methodDTO.name
    }

    override fun getSectionId(): Long {
        return methodDTO.sectionID
    }
}
