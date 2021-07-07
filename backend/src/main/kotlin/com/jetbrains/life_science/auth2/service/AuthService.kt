package com.jetbrains.life_science.auth2.service

interface AuthService {

    fun login(credentials: AuthCredentials): AuthTokens

}