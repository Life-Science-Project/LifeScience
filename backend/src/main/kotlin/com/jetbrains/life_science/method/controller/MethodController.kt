package com.jetbrains.life_science.method.controller

import com.jetbrains.life_science.method.dto.MethodDTO
import com.jetbrains.life_science.method.dto.MethodDTOToInfoAdapter
import com.jetbrains.life_science.method.service.MethodService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/methods")
class MethodController(
    val service: MethodService
) {

    @PostMapping
    fun create(@Validated @RequestBody dto: MethodDTO) {
        service.create(MethodDTOToInfoAdapter(dto))
    }
}
