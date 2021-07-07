package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.auth2.jwt.JWTService
import com.jetbrains.life_science.auth2.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.auth2.refresh.service.RefreshTokenService
import com.jetbrains.life_science.user.master.entity.UserCredentials
import com.jetbrains.life_science.user.master.service.UserCredentialsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    val authenticationManager: AuthenticationManager,
    val jwtService: JWTService,
    val refreshTokenService: RefreshTokenService,
    val userCredentialsService: UserCredentialsService
) : AuthService {

    override fun login(credentials: AuthCredentials): AuthTokens {
        setAuthentication(credentials)
        val userCredentials = userCredentialsService.getByEmail(credentials.email)
        return generateTokens(userCredentials)
    }

    override fun refreshTokens(userCredentials: UserCredentials, refreshTokenCode: RefreshTokenCode): AuthTokens {
        refreshTokenService.validateRefreshToken(userCredentials, refreshTokenCode)
        return generateTokens(userCredentials)
    }

    private fun generateTokens(userCredentials: UserCredentials): AuthTokens {
        val jwt = jwtService.generateJWT(userCredentials.email)
        val refreshToken = refreshTokenService.updateRefreshToken(userCredentials)
        return AuthTokens(jwt, refreshToken)
    }

    private fun setAuthentication(authInfo: AuthCredentials) {
        val loginPasswordToken = UsernamePasswordAuthenticationToken(authInfo.email, authInfo.password)
        authenticationManager.authenticate(loginPasswordToken)
    }

}