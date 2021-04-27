package com.jetbrains.life_science.config.jwt

import com.jetbrains.life_science.user.details.entity.User
import org.springframework.security.core.GrantedAuthority

class JWTResponse(
    var accessToken: String,
    private val user: User,
    val authorities: Collection<GrantedAuthority>
) {
    var type = "Bearer"

    val firstName
        get() = user.firstName

    val lastName
        get() = user.lastName
}
