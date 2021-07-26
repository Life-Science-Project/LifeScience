package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.controller.user.view.UserFullView
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.exception.not_found.UserPersonalDataNotFoundException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    val userViewMapper: UserViewMapper,
) {
    @GetMapping("/current")
    fun getCurrentUser(
        @AuthenticationPrincipal user: Credentials
    ): UserFullView {
        val userData = getUserData(user)
        return userViewMapper.toFullView(userData)
    }

    private fun getUserData(user: Credentials): UserPersonalData {
        return user.userPersonalData ?: throw UserPersonalDataNotFoundException(
            "UserData not found for " +
                "credentials with id ${user.id}"
        )
    }
}
