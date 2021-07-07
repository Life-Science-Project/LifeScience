package com.jetbrains.life_science.controller.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/ping")
class PingController {


    @GetMapping
    fun getResult(principal: Principal) {

    }

}