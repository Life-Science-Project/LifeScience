package com.jetbrains.life_science.user.credentials.factory

import com.jetbrains.life_science.user.credentials.entity.NewUserInfo
import com.jetbrains.life_science.user.credentials.entity.Role
import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.user.details.factory.UserFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserCredentialsFactory(
    val userFactory: UserFactory,
    val encoder: PasswordEncoder
) {

    fun createUser(info: NewUserInfo, roles: MutableCollection<Role>): UserCredentials {
        val password = encoder.encode(info.password)
        val userCredentials = UserCredentials(0, info.email, password, roles)
        userCredentials.user = userFactory.create(info.firstName, info.lastName)
        return userCredentials
    }
}
