package com.jetbrains.life_science.auth

import com.jetbrains.life_science.config.jwt.JWTService
import com.jetbrains.life_science.user.master.dto.NewUserDTO
import com.jetbrains.life_science.user.master.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.service.UserService
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
    var jwtService: JWTService,
    val userService: UserService,
    val authResponseFactory: AuthResponseFactory
) {

    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody authRequest: AuthRequest): AuthResponse {
        authenticate(authRequest)
        val user = userService.getByEmail(authRequest.email)
        return authResponse(user)
    }

    @PostMapping("/signup")
    fun registerUser(@Validated @RequestBody userDto: NewUserDTO): AuthResponse {
        val user = userService.createUser(NewUserDTOToInfoAdapter(userDto))
        return authResponse(user, true)
    }

    @PostMapping("/refresh")
    fun refreshToken(@Validated @RequestBody refreshRequest: AuthRefreshRequest): AuthResponse {
        val username = jwtService.getUserNameFromExpiredJwtToken(refreshRequest.jwt)
        val user = userService.getByEmail(username)
        if (user.refreshToken != refreshRequest.refreshToken) {
            throw BadCredentialsException("Invalid refresh token")
        }
        return authResponse(user, true)
    }

    private fun authResponse(user: User, updateRefresh: Boolean = false): AuthResponse {
        val tokens = jwtService.generateAuthTokens(user.email)

        val oldRefreshToken = user.refreshToken
        if (updateRefresh || oldRefreshToken == null) {
            userService.updateRefreshToken(tokens.refreshToken, user.email)
        } else {
            tokens.refreshToken = oldRefreshToken
        }
        return authResponseFactory.create(tokens, user)
    }

    private fun authenticate(authInfo: AuthRequest) {
        val token = UsernamePasswordAuthenticationToken(authInfo.email, authInfo.password)
        authenticationManager.authenticate(token)
    }
}
