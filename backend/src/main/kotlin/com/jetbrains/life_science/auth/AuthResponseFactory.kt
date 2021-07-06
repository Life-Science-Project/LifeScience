package com.jetbrains.life_science.auth

import com.jetbrains.life_science.user.user.entity.User
import com.jetbrains.life_science.user.user.view.UserViewMapper
import org.springframework.stereotype.Component

@Component
class AuthResponseFactory(
    val userViewMapper: UserViewMapper
) {

    fun create(tokens: AuthTokens, user: User): AuthResponse {
        val userView = userViewMapper.createView(user)
        return AuthResponse(tokens, userView)
    }
}
