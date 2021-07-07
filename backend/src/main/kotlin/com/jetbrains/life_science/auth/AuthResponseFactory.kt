package com.jetbrains.life_science.auth

import com.jetbrains.life_science.auth2.service.AuthTokens
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.view.UserViewMapper
import org.springframework.stereotype.Component

@Component
class AuthResponseFactory(
    val userViewMapper: UserViewMapper
) {

    fun create(tokens: AuthTokens, user: User): AuthResponseView {
        val userView = userViewMapper.createView(user)
        return AuthResponseView(tokens, userView)
    }
}
