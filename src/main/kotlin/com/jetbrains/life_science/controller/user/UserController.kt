package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.controller.user.view.UserFullView
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.exception.auth.InvalidCredentialsException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    val userViewMapper: UserViewMapper
) {
    @GetMapping("/current")
    fun getCurrentUser(
        @AuthenticationPrincipal user: Credentials
    ): UserFullView {
        val userData = user.userPersonalData ?: throw InvalidCredentialsException()
        return userViewMapper.toFullView(userData)
    }
}
