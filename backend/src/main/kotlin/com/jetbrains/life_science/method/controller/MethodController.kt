package com.jetbrains.life_science.method.controller

import com.jetbrains.life_science.method.dto.MethodDTO
import com.jetbrains.life_science.method.dto.MethodDTOToInfoAdapter
import com.jetbrains.life_science.method.service.MethodService
import com.jetbrains.life_science.method.view.MethodView
import com.jetbrains.life_science.method.view.MethodViewMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/method")
class MethodController(
    val methodService: MethodService,
    val methodViewMapper: MethodViewMapper,
) {

    @PostMapping
    fun addMethod(@RequestBody @Validated methodDTO: MethodDTO) {
        methodService.create(MethodDTOToInfoAdapter(methodDTO))
    }

    @GetMapping("/{id}")
    fun getMethod(@PathVariable id: Long): MethodView {
        val method = methodService.getMethod(id)
        return methodViewMapper.createView(method)
    }

    @DeleteMapping("/{id}")
    fun deleteMethod(@PathVariable id: Long) {
        methodService.deleteByID(id)
    }
}
