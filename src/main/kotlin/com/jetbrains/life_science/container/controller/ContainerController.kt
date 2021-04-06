package com.jetbrains.life_science.container.controller

import com.jetbrains.life_science.container.dto.ContainerDTO
import com.jetbrains.life_science.container.dto.ContainerDTOToInfoAdapter
import com.jetbrains.life_science.container.service.ContainerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/container")
class ContainerController(
    val service: ContainerService
) {

    @PostMapping
    fun create(@RequestBody dto: ContainerDTO) {
        service.create(ContainerDTOToInfoAdapter(dto))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.deleteById(id)
    }

}
