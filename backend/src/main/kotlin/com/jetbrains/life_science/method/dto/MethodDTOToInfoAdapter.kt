package com.jetbrains.life_science.method.dto

import com.jetbrains.life_science.method.service.MethodInfo

class MethodDTOToInfoAdapter(val dto: MethodDTO) : MethodInfo {
    override val sectionId: Long
        get() = dto.sectionId
}
