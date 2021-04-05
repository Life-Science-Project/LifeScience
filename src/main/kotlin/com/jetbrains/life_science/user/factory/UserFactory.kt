package com.jetbrains.life_science.user.factory

import com.jetbrains.life_science.user.entity.Role
import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.user.entity.UserInfo
import org.springframework.stereotype.Component

@Component
class UserFactory {
    fun createUser(userInfo: UserInfo, roles: MutableCollection<Role>): User {
        return User(
            userInfo.getID(), userInfo.getUsername(), userInfo.getFirstName(), userInfo.getLastName(),
            userInfo.getEmail(), userInfo.getPassword(), userInfo.getEnabled(), roles
        )
    }
}