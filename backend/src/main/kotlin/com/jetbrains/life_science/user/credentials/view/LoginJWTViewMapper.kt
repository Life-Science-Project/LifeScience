package com.jetbrains.life_science.user.credentials.view

import com.jetbrains.life_science.config.jwt.JWTResponse
import com.jetbrains.life_science.user.details.entity.User
import com.jetbrains.life_science.user.details.view.UserViewMapper
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class LoginJWTViewMapper(
    val userViewMapper: UserViewMapper
) {

    fun toView(jwt: String, authorities: List<SimpleGrantedAuthority>, user: User): LoginJWTView {
        val userView = userViewMapper.createShortView(user)
        val jwtResponse = JWTResponse(jwt, authorities)
        return LoginJWTView(jwtResponse, userView)
    }
}
