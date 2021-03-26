package com.jetbrains.life_science.section

import com.jetbrains.life_science.method.dto.MethodDTO
import com.jetbrains.life_science.method.dto.MethodDTOToInfoWrapper
import com.jetbrains.life_science.section.dto.SectionDTO
import com.jetbrains.life_science.section.dto.SectionDTOToInfoWrapper
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionServiceImpl
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/section")
class SectionController(
    val sectionService: SectionServiceImpl
) {
    @PostMapping("")
    fun addMethod (@RequestBody @Valid sectionDTO: SectionDTO) {
        sectionService.addSection(SectionDTOToInfoWrapper(sectionDTO))
    }

    @GetMapping("/test")
    fun getTestData(){
        sectionService.addSection(SectionDTOToInfoWrapper(SectionDTO("sasha2", null)))
    }


}