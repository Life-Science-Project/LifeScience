package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.controller.user.view.UserFullView
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.service.UserPersonalDataService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    val userViewMapper: UserViewMapper,
    val userPersonalDataService: UserPersonalDataService
) {

    @GetMapping("/current")
    @Transactional(readOnly = true)
    fun getCurrentUser(
        @AuthenticationPrincipal credentials: Credentials
    ): UserFullView {
        val userData = userPersonalDataService.getByCredentials(credentials)
        return userViewMapper.toFullView(credentials, userData)
    }
}
