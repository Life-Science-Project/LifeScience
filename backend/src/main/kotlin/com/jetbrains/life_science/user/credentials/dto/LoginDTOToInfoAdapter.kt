package com.jetbrains.life_science.user.credentials.dto

import com.jetbrains.life_science.user.credentials.entity.LoginInfo

class LoginDTOToInfoAdapter(private val loginDTO: LoginDTO) : LoginInfo {

    override val id: Long
        get() = 0

    override val email: String
        get() = loginDTO.email

    override val password: String
        get() = loginDTO.password
}
