package com.jetbrains.life_science.auth.jwt

interface JWTService {

    fun generateJWT(username: String): JWTCode

    fun validateJwtToken(authToken: String): Boolean

    fun getUserNameFromJwtToken(token: String): String
}
