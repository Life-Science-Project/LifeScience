package com.jetbrains.life_science.container.controller

import com.jetbrains.life_science.container.dto.ContainerDTO
import com.jetbrains.life_science.container.dto.ContainerDTOToInfoAdapter
import com.jetbrains.life_science.container.service.ContainerService
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/container")
class ContainerController(
    val service: ContainerService
) {

    @PostMapping
    fun create(@RequestBody dto: ContainerDTO) {
        service.create(ContainerDTOToInfoAdapter(dto))
    }



}
