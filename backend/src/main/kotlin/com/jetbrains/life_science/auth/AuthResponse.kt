package com.jetbrains.life_science.auth

import com.jetbrains.life_science.user.master.view.UserView

class AuthResponse(
    val tokens: AuthTokens,
    val user: UserView,
    val roles: List<String>
)
