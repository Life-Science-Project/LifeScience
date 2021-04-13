package com.jetbrains.life_science.version.dto

import com.jetbrains.life_science.version.service.MethodVersionInfo

class MethodVersionDTOToInfoAdapter(
    val dto: MethodVersionDTO
) : MethodVersionInfo {

    override val methodId: Long
        get() = dto.methodId

    override val name: String
        get() = dto.name
}
