package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.auth2.jwt.JWTService
import com.jetbrains.life_science.auth2.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.auth2.refresh.service.RefreshTokenService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    val authenticationManager: AuthenticationManager,
    val jwtService: JWTService,
    val refreshTokenService: RefreshTokenService,
    val userCredentialsService: CredentialsService
) : AuthService {

    override fun login(authCredentials: AuthCredentials): AuthTokens {
        setAuthentication(authCredentials)
        val userCredentials = userCredentialsService.getByEmail(authCredentials.email)
        return generateTokens(userCredentials)
    }

    override fun register(credentials: Credentials): AuthTokens {
        val jwt = jwtService.generateJWT(credentials.email)
        val refreshToken = refreshTokenService.createRefreshToken(credentials)
        return AuthTokens(jwt, refreshToken)
    }

    override fun refreshTokens(userCredentials: Credentials, refreshTokenCode: RefreshTokenCode): AuthTokens {
        refreshTokenService.validateRefreshToken(userCredentials, refreshTokenCode)
        return generateTokens(userCredentials)
    }

    private fun generateTokens(userCredentials: Credentials): AuthTokens {
        val jwt = jwtService.generateJWT(userCredentials.email)
        val refreshToken = refreshTokenService.updateRefreshToken(userCredentials)
        return AuthTokens(jwt, refreshToken)
    }

    private fun setAuthentication(authInfo: AuthCredentials) {
        val loginPasswordToken = UsernamePasswordAuthenticationToken(authInfo.email, authInfo.password)
        authenticationManager.authenticate(loginPasswordToken)
    }
}
