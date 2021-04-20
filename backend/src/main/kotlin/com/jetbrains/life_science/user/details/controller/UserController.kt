package com.jetbrains.life_science.user.details.controller

import com.jetbrains.life_science.user.details.dto.AddDetailsDTO
import com.jetbrains.life_science.user.details.dto.AddDetailsDTOToInfoAdapter
import com.jetbrains.life_science.user.details.service.UserService
import com.jetbrains.life_science.util.email
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {

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
