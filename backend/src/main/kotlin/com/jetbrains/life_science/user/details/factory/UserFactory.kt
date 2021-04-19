package com.jetbrains.life_science.user.details.factory

import com.jetbrains.life_science.user.details.entity.User
import org.springframework.stereotype.Component

@Component
class UserFactory {
    fun create(firstName: String, lastName: String): User {
        return User(
            id = 0,
            firstName = firstName,
            lastName = lastName,
            organisations = mutableListOf(),
            positions = mutableListOf()
        )
    }
}
