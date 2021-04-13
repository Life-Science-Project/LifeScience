package com.jetbrains.life_science.version.contoller

import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.version.dto.MethodVersionDTO
import com.jetbrains.life_science.version.dto.MethodVersionDTOToInfoAdapter
import com.jetbrains.life_science.version.service.MethodVersionService
import com.jetbrains.life_science.version.view.MethodVersionView
import com.jetbrains.life_science.version.view.MethodVersionViewMapper
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/versions")
class MethodVersionController(
    val service: MethodVersionService,
    val mapper: MethodVersionViewMapper,
    val userService: UserService
) {
    @GetMapping("/{methodId}")
    fun getApprovedVersion(@PathVariable methodId: Long): MethodVersionView {
        val version = service.getPublishedVersion(methodId)
        return mapper.createView(version)
    }

    @PostMapping("/create/blank")
    fun createBlank(@Validated @RequestBody dto: MethodVersionDTO, principal: Principal) {
        val user = userService.getByName(principal.name)
        service.createBlank(MethodVersionDTOToInfoAdapter(dto, user))
    }

    @PostMapping("/create/copy/{methodId}")
    fun createCopy(@PathVariable methodId: Long) {
        service.createCopy(methodId)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/approve/{id}")
    fun approve(@PathVariable id: Long) {
        service.approve(id)
    }
}
