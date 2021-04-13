package com.jetbrains.life_science.version.contoller

import com.jetbrains.life_science.version.dto.MethodVersionDTO
import com.jetbrains.life_science.version.dto.MethodVersionDTOToInfoAdapter
import com.jetbrains.life_science.version.service.MethodVersionService
import com.jetbrains.life_science.version.view.MethodVersionView
import com.jetbrains.life_science.version.view.MethodVersionViewMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/versions")
class MethodVersionController(
    val service: MethodVersionService,
    val mapper: MethodVersionViewMapper
) {
    @GetMapping("/{methodId}")
    fun getApprovedVersion(@PathVariable methodId: Long): MethodVersionView {
        val version = service.getPublishedVersion(methodId)
        return mapper.createView(version)
    }

    @PostMapping("/create/blank")
    fun createBlank(@Validated @RequestBody dto: MethodVersionDTO) {
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
