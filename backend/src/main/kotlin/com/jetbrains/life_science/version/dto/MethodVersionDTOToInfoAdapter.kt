package com.jetbrains.life_science.version.dto

import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.version.service.MethodVersionInfo

class MethodVersionDTOToInfoAdapter(
    val dto: MethodVersionDTO,
    override val user: User
) : MethodVersionInfo {

    override val methodId: Long
        get() = dto.methodId

    override val name: String
        get() = dto.name
}
