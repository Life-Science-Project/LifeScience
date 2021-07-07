package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.auth2.jwt.JWTService
import com.jetbrains.life_science.auth2.refresh.RefreshTokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
        val authenticationManager: AuthenticationManager,
        val jwtService: JWTService,
        val refreshTokenService: RefreshTokenService
) : AuthService {

    override fun login(credentials: AuthCredentials): AuthTokens {
        setAuthentication(credentials)
        val jwt = jwtService.generateJWT(credentials.email)
        val refreshToken = refreshTokenService.updateRefreshToken()
        return AuthTokens(jwt, refreshToken)
    }


    private fun setAuthentication(authInfo: AuthCredentials) {
        val loginPasswordToken = UsernamePasswordAuthenticationToken(authInfo.email, authInfo.password)
        authenticationManager.authenticate(loginPasswordToken)
    }

}