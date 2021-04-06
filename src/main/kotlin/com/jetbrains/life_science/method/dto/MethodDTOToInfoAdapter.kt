package com.jetbrains.life_science.method.dto

import com.jetbrains.life_science.method.entity.MethodInfo

class MethodDTOToInfoAdapter(private val methodDTO: MethodDTO) : MethodInfo {

    override val id: Long
        get() = 0

    override val name: String
        get() = methodDTO.name

    override val sectionId: Long
        get() = methodDTO.sectionID
}
