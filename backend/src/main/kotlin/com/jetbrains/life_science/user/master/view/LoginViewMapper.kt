package com.jetbrains.life_science.user.master.view

import com.jetbrains.life_science.user.master.entity.User
import org.springframework.stereotype.Component

@Component
class LoginViewMapper(
    val userViewMapper: UserViewMapper
) {

    fun createView(jwt: String, user: User): LoginView {
        val userView = userViewMapper.createView(user)
        return LoginView(jwt, userView, user.roles.map { it.name })
    }
}
