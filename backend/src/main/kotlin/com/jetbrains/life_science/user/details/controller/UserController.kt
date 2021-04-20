package com.jetbrains.life_science.user.details.controller

import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.details.dto.AddDetailsDTO
import com.jetbrains.life_science.user.details.dto.AddDetailsDTOToInfoAdapter
import com.jetbrains.life_science.user.details.service.UserService
import com.jetbrains.life_science.user.details.view.UserView
import com.jetbrains.life_science.user.details.view.UserViewMapper
import com.jetbrains.life_science.util.email
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/user")
class UserController(
    val userService: UserService,
    val userCredentialsService: UserCredentialsService,
    val mapper: UserViewMapper
) {

    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId: Long,
    ): UserView {
        val user = userService.getById(userId)
        return mapper.createView(user)
    }

    @PostMapping("/update")
    fun addDetails(
        @Validated @RequestBody addDetailsDTO: AddDetailsDTO,
        principal: Principal
    ) {
        userService.update(
            AddDetailsDTOToInfoAdapter(addDetailsDTO),
            userService.getByEmail(principal.email)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable userId: Long
    ) {
        val user = userCredentialsService.getById(userId).user
        userCredentialsService.delete(userId)
        userService.delete(user)
    }

    @DeleteMapping("")
    fun deleteProfile(
        principal: Principal
    ) {
        val userCredentialId = userCredentialsService.getByEmail(principal.email).id
        val user = userService.getByEmail(principal.email)
        userCredentialsService.delete(userCredentialId)
        userService.delete(user)
    }
}
