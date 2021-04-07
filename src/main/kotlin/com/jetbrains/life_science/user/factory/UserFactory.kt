package com.jetbrains.life_science.user.factory

import com.jetbrains.life_science.user.entity.Role
import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.user.entity.NewUserInfo
import org.springframework.stereotype.Component

@Component
class UserFactory {
    fun createUser(newUserInfo: NewUserInfo, roles: MutableCollection<Role>): User {
        return User(
            newUserInfo.getID(), newUserInfo.getUsername(), newUserInfo.getFirstName(), newUserInfo.getLastName(),
            newUserInfo.getEmail(), newUserInfo.getPassword(), newUserInfo.getEnabled(), roles
        )
    }
}