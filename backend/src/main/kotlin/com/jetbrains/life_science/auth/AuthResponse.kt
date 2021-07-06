package com.jetbrains.life_science.auth

import com.jetbrains.life_science.user.user.view.UserView

data class AuthResponse(
    val tokens: AuthTokens,
    val user: UserView
)
