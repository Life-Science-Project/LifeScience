package com.jetbrains.life_science.user.dto

import com.jetbrains.life_science.user.entity.NewUserInfo
import org.springframework.security.crypto.password.PasswordEncoder

class NewUserDTOToInfoAdapter(
    private val userDTO: NewUserDTO,
    private val encoder: PasswordEncoder
) : NewUserInfo {

    override fun getID(): Long {
        return 0
    }

    override fun getUsername(): String {
        return userDTO.username
    }

    override fun getFirstName(): String {
        return userDTO.firstName
    }

    override fun getLastName(): String {
        return userDTO.lastName
    }

    override fun getEmail(): String {
        return userDTO.email
    }

    override fun getPassword(): String {
        return encoder.encode(userDTO.password)
    }

    override fun getEnabled(): Boolean {
        return true
    }
}
