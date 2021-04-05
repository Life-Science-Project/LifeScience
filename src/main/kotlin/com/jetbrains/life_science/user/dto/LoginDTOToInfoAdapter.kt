package com.jetbrains.life_science.user.dto

import com.jetbrains.life_science.user.entity.UserInfo

class LoginDTOToInfoAdapter(private val loginDTO: LoginDTO) : UserInfo {

    override fun getID(): Long {
        return 0
    }

    override fun getUsername(): String {
        return loginDTO.username
    }

    override fun getPassword(): String {
        return loginDTO.password
    }

    override fun getFirstName(): String? {
        return null
    }

    override fun getLastName(): String? {
        return null
    }

    override fun getEmail(): String? {
        return null
    }

    override fun getEnabled(): Boolean {
        return true
    }
}