package com.jetbrains.life_science.config.jwt.dto

import com.jetbrains.life_science.config.jwt.entity.ForbiddenJWTInfo

class LogOutDTOToInfoAdapter(
    val dto: LogOutDTO
) : ForbiddenJWTInfo {

    override val token: String
        get() = dto.token
}
