package com.jetbrains.life_science.container.controller

import com.jetbrains.life_science.container.dto.ContainerDTO
import com.jetbrains.life_science.container.dto.ContainerDTOToInfoAdapter
import com.jetbrains.life_science.container.dto.ContainerDTOToUpdateInfoAdapter
import com.jetbrains.life_science.container.dto.ContainerUpdateDTO
import com.jetbrains.life_science.container.service.ContainerService
import com.jetbrains.life_science.container.view.ContainerView
import com.jetbrains.life_science.container.view.ContainerViewMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/container")
class ContainerController(
    val service: ContainerService,
    val containerViewMapper: ContainerViewMapper
) {

    @PostMapping
    fun create(@Validated @RequestBody dto: ContainerDTO) {
        service.create(ContainerDTOToInfoAdapter(dto))
    }

    @GetMapping("/{id}")
    fun getContainer(@PathVariable id: Long): ContainerView {
        return containerViewMapper.createView(service.getById(id))
    }

    @PutMapping
    fun updateContainer(@Validated @RequestBody dto: ContainerUpdateDTO) {
        service.update(ContainerDTOToUpdateInfoAdapter(dto))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.deleteById(id)
    }
}
