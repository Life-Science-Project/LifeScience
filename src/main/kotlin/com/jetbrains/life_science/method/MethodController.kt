package com.jetbrains.life_science.method

import com.jetbrains.life_science.article.dto.ArticleDTO
import com.jetbrains.life_science.method.dto.MethodDTO
import com.jetbrains.life_science.method.dto.MethodDTOToInfoWrapper
import com.jetbrains.life_science.method.service.MethodService
import com.jetbrains.life_science.method.view.MethodView
import com.jetbrains.life_science.method.view.MethodViewMapper
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/method")
class MethodController (val methodService: MethodService) {

    @PostMapping("")
    fun addMethod (@RequestBody @Valid methodDTO: MethodDTO) {
        methodService.addMethod(MethodDTOToInfoWrapper(methodDTO))
    }

    @GetMapping("/{id}")
    fun getMethod(@PathVariable id:Long) : MethodView {
        val method = methodService.getMethod(id)
        return MethodViewMapper.createView(method)
    }

    @DeleteMapping("/{id}")
    fun deleteMethod(@PathVariable id:Long) {
        methodService.deleteMethod(id)
    }

    @GetMapping("/test")
    fun getTestData() {
        val dto = ArticleDTO()
        methodService.addMethod(MethodDTOToInfoWrapper(MethodDTO("test", 1, dto)))
    }
}