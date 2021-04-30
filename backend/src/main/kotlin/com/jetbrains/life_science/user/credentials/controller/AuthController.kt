package com.jetbrains.life_science.user.credentials.controller

import com.jetbrains.life_science.config.jwt.JWTProvider
import com.jetbrains.life_science.config.jwt.dto.LogOutDTO
import com.jetbrains.life_science.config.jwt.dto.LogOutDTOToInfoAdapter
import com.jetbrains.life_science.config.jwt.service.ForbiddenJWTService
import com.jetbrains.life_science.user.credentials.dto.LoginDTO
import com.jetbrains.life_science.user.credentials.dto.NewUserDTO
import com.jetbrains.life_science.user.credentials.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.credentials.view.LoginJWTView
import com.jetbrains.life_science.user.credentials.view.LoginJWTViewMapper
import com.jetbrains.life_science.user.details.service.UserService
import com.jetbrains.life_science.util.email
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    var jwtProvider: JWTProvider,
    val userCredentialsService: UserCredentialsService,
    val forbiddenJWTService: ForbiddenJWTService,
    val userService: UserService,
    val loginJWTViewMapper: LoginJWTViewMapper
) {

    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody loginRequest: LoginDTO): LoginJWTView {
        val token = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        val authentication = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtProvider.generateJwtToken(loginRequest.email)
        val authorities = authentication.authorities
            .map { role -> SimpleGrantedAuthority(role.authority) }.toList()
        val user = userService.getByEmail(authentication.email)
        return loginJWTViewMapper.toView(jwt, authorities, user)
    }

    @PostMapping("/logout")
    fun logout(@Validated @RequestBody dto: LogOutDTO) {
        forbiddenJWTService.saveIfNeed(LogOutDTOToInfoAdapter(dto))
    }

    @PostMapping("/signup")
    fun registerUser(@Validated @RequestBody newUserDto: NewUserDTO) {
        userCredentialsService.createUser(NewUserDTOToInfoAdapter(newUserDto))
    }
}
