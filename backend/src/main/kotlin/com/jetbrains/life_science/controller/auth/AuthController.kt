package com.jetbrains.life_science.controller.auth

import com.jetbrains.life_science.auth.AuthRefreshRequest
import com.jetbrains.life_science.auth.AuthRequest
import com.jetbrains.life_science.auth.AuthResponseView
import com.jetbrains.life_science.auth.AuthResponseFactory
import com.jetbrains.life_science.auth.service.AuthResponseToCredentialsAdapter
import com.jetbrains.life_science.auth.service.AuthService
import com.jetbrains.life_science.auth2.jwt.JWTServiceImpl
import com.jetbrains.life_science.user.master.dto.NewUserDTO
import com.jetbrains.life_science.user.master.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
        val authenticationManager: AuthenticationManager,
        var jwtService: JWTServiceImpl,
        val userService: UserService,
        val authResponseFactory: AuthResponseFactory,
        val authService: AuthService
) {

    @Operation(summary = "Sign in")
    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody authRequest: AuthRequest): AuthResponseView {
        setAuthentication(authRequest)
        val (jwt, refreshToken) = authService.login(AuthResponseToCredentialsAdapter(authRequest))

        return authResponse(user)
    }

    @Operation(summary = "Sign up")
    @PostMapping("/signup")
    fun registerUser(@Validated @RequestBody userDto: NewUserDTO): AuthResponseView {
        val user = userService.createUser(NewUserDTOToInfoAdapter(userDto))
        return authResponse(user, true)
    }

    @Operation(summary = "Refreshes JWT and Refresh tokens")
    @PostMapping("/refresh")
    fun refreshToken(@Validated @RequestBody refreshRequest: AuthRefreshRequest): AuthResponseView {
        val username = try {
            jwtService.getUserNameFromExpiredJwtToken(refreshRequest.jwt)
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid JWT token")
        }
        val user = userService.getByEmail(username)
        if (user.refreshToken != refreshRequest.refreshToken) {
            throw BadCredentialsException("Invalid refresh token")
        }
        return authResponse(user, true)
    }

    private fun authResponse(user: User, updateRefresh: Boolean = false): AuthResponseView {
        val tokens = jwtService.generateAuthTokens(user.email)

        val oldRefreshToken = user.refreshToken
        if (updateRefresh || oldRefreshToken == null) {
            userService.updateRefreshToken(tokens.refreshToken, user.email)
        } else {
            tokens.refreshToken = oldRefreshToken
        }
        return authResponseFactory.create(tokens, user)
    }

    private fun setAuthentication(authInfo: AuthRequest) {
        val loginPasswordToken = UsernamePasswordAuthenticationToken(authInfo.email, authInfo.password)
        authenticationManager.authenticate(loginPasswordToken)
    }
}
