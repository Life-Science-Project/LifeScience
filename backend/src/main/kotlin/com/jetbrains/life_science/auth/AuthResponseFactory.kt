package com.jetbrains.life_science.auth

import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.view.UserViewMapper
import org.springframework.stereotype.Component

@Component
class AuthResponseFactory(
    val userViewMapper: UserViewMapper
) {

    fun create(tokens: AuthTokens, user: User): AuthResponse {
        val userView = userViewMapper.createView(user)
        return AuthResponse(tokens, userView, user.roles.map { it.name })
    }
}
