package com.jetbrains.life_science.version.contoller

import com.jetbrains.life_science.version.dto.MethodVersionDTO
import com.jetbrains.life_science.version.dto.MethodVersionDTOToInfoAdapter
import com.jetbrains.life_science.version.service.MethodVersionService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/versions")
class MethodVersionController(
    val service: MethodVersionService
) {


    @PostMapping("/create/blank")
    fun createBlack(@Validated @RequestBody dto: MethodVersionDTO) {
        service.createBlack(MethodVersionDTOToInfoAdapter(dto))
    }

    @PostMapping("/create/copy/{methodId}")
    fun createCopy(@PathVariable methodId: Long) {
        service.createCopy(methodId)
    }

    @PutMapping("/approve/{id}")
    fun approve(@PathVariable id: Long) {
        service.approve(id)
    }

}
