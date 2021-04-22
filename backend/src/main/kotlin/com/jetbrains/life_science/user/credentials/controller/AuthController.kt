package com.jetbrains.life_science.user.credentials.controller

import com.jetbrains.life_science.config.jwt.JWTProvider
import com.jetbrains.life_science.config.jwt.JWTResponse
import com.jetbrains.life_science.user.credentials.dto.LoginDTO
import com.jetbrains.life_science.user.credentials.dto.NewUserDTO
import com.jetbrains.life_science.user.credentials.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    var jwtProvider: JWTProvider,
    val userCredentialsService: UserCredentialsService
) {

    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody loginRequest: LoginDTO): JWTResponse {
        val token = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        val authentication = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtProvider.generateJwtToken(loginRequest.email)
        val authorities = authentication.authorities
            .map { role -> SimpleGrantedAuthority(role.authority) }.toList()
        return JWTResponse(jwt, loginRequest.email, authorities)
    }

    @PostMapping("/signup")
    fun registerUser(@Validated @RequestBody newUserDto: NewUserDTO) {
        userCredentialsService.createUser(NewUserDTOToInfoAdapter(newUserDto))
    }
}
