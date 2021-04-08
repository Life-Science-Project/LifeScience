package com.jetbrains.life_science.config.jwt

import org.springframework.security.core.GrantedAuthority

class JWTResponse(
    var accessToken: String?,
    var username: String?,
    val authorities: Collection<GrantedAuthority>
) {
    var type = "Bearer"
}
