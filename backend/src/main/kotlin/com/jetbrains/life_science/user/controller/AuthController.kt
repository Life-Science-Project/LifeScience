package com.jetbrains.life_science.user.controller

import com.jetbrains.life_science.config.jwt.JWTProvider
import com.jetbrains.life_science.user.master.dto.LoginDTO
import com.jetbrains.life_science.user.master.dto.NewUserDTO
import com.jetbrains.life_science.user.master.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.user.master.view.LoginView
import com.jetbrains.life_science.user.master.view.LoginViewMapper
import org.springframework.security.authentication.AuthenticationManager
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
    var jwtProvider: JWTProvider,
    val userService: UserService,
    val loginViewMapper: LoginViewMapper
) {

    @PostMapping("/signin")
    fun authenticateUser(@Validated @RequestBody loginInfo: LoginDTO): LoginView {
        authenticate(loginInfo)
        val user = userService.getByEmail(loginInfo.email)
        return loginResponse(user)
    }

    @PostMapping("/signup")
    fun registerUser(@Validated @RequestBody userDto: NewUserDTO): LoginView {
        val user = userService.createUser(NewUserDTOToInfoAdapter(userDto))
        return loginResponse(user)
    }

    private fun loginResponse(user: User): LoginView {
        val jwt = jwtProvider.generateJwtToken(user.email)
        return loginViewMapper.createView(jwt, user)
    }

    private fun authenticate(loginInfo: LoginDTO) {
        val token = UsernamePasswordAuthenticationToken(loginInfo.email, loginInfo.password)
        authenticationManager.authenticate(token)
    }
}
