package com.jetbrains.life_science.user.dto

import com.jetbrains.life_science.user.entity.LoginInfo

class LoginDTOToInfoAdapter(private val loginDTO: LoginDTO) : LoginInfo {

    override fun getId(): Long {
        return 0
    }

    override fun getUsername(): String {
        return loginDTO.username
    }

    override fun getPassword(): String {
        return loginDTO.password
    }
}
