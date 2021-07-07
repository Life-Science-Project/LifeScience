package com.jetbrains.life_science.auth

import com.jetbrains.life_science.auth2.jwt.JWTCode


class AuthResponseView(jwtCode: JWTCode) {
    val accessToken: String = jwtCode.code
}
