package com.jetbrains.life_science.user.details.controller

import com.jetbrains.life_science.user.details.dto.AddDetailsDTO
import com.jetbrains.life_science.user.details.dto.AddDetailsDTOToInfoAdapter
import com.jetbrains.life_science.user.details.service.UserService
import com.jetbrains.life_science.user.details.view.UserView
import com.jetbrains.life_science.user.details.view.UserViewMapper
import com.jetbrains.life_science.util.email
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService,
    val mapper: UserViewMapper
) {

    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId: Long,
    ): UserView {
        val user = userService.getById(userId)
        return mapper.createView(user)
    }

    @PostMapping("/add_details")
    fun addDetails(
        @RequestBody addDetailsDTO: AddDetailsDTO,
        principal: Principal
    ) {
        userService.update(
            AddDetailsDTOToInfoAdapter(addDetailsDTO),
            userService.getByEmail(principal.email)
        )
    }
}
