package com.jetbrains.life_science.user.credentials.dto

import com.jetbrains.life_science.user.credentials.entity.LoginInfo

class LoginDTOToInfoAdapter(private val loginDTO: LoginDTO) : LoginInfo {

    override fun getId(): Long {
        return 0
    }

    override fun getEmail(): String {
        return loginDTO.email
    }

    override fun getPassword(): String {
        return loginDTO.password
    }
}
