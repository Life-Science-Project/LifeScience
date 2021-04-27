package com.jetbrains.life_science.user.credentials.view

import com.jetbrains.life_science.config.jwt.JWTResponse
import com.jetbrains.life_science.user.details.view.UserShortView

class LoginJWTView(
    val jwtResponse: JWTResponse,
    val userView: UserShortView
)
