package com.jetbrains.life_science.controller.auth.view

import com.jetbrains.life_science.auth.jwt.JWTCode
import org.springframework.stereotype.Component

@Component
class AccessTokenViewMapper {

    fun toView(jwtCode: JWTCode): AccessTokenView {
        return AccessTokenView(jwtCode.code)
    }
}
