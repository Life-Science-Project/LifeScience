package com.jetbrains.life_science.user.master.dto

import com.jetbrains.life_science.user.master.service.NewUserInfo

class NewUserDTOToInfoAdapter(
    dto: NewUserDTO
) : NewUserInfo {

    override val email = dto.email

    override val password = dto.password

    override val firstName = dto.firstName

    override val lastName = dto.lastName
}
