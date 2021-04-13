package com.jetbrains.life_science.method.controller

import com.jetbrains.life_science.method.dto.MethodDTO
import com.jetbrains.life_science.method.dto.MethodDTOToInfoAdapter
import com.jetbrains.life_science.method.service.MethodService
import com.jetbrains.life_science.method.view.MethodView
import com.jetbrains.life_science.method.view.MethodViewMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/methods")
class MethodController(
    val service: MethodService,
    val mapper: MethodViewMapper
) {

    @GetMapping("/{sectionId}")
    fun getBySection(@PathVariable sectionId: Long): List<MethodView> {
        return service.getBySectionId(sectionId).map { mapper.createView(it) }
    }

    @PostMapping
    fun create(@Validated @RequestBody dto: MethodDTO) {
        service.create(MethodDTOToInfoAdapter(dto))
    }
}
