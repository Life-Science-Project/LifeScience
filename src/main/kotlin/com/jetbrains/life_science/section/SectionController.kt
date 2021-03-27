package com.jetbrains.life_science.section

import com.jetbrains.life_science.section.dto.SectionDTO
import com.jetbrains.life_science.section.dto.SectionDTOToInfoWrapper
import com.jetbrains.life_science.section.service.SectionServiceImpl
import com.jetbrains.life_science.section.view.SectionView
import com.jetbrains.life_science.section.view.SectionViewMapper
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/section")
class SectionController(
    val sectionService: SectionServiceImpl
) {
    @PostMapping("")
    fun addSection (@RequestBody @Valid sectionDTO: SectionDTO) {
        sectionService.addSection(SectionDTOToInfoWrapper(sectionDTO))
    }

    @GetMapping("/{id}")
    fun getChildren(@PathVariable id: Long) : SectionView {
        val section = sectionService.getSection(id)
        val children = sectionService.getChildren(id)
        return SectionViewMapper.createView(section, children)
    }

    @GetMapping("/test")
    fun getTestData(){
        sectionService.addSection(SectionDTOToInfoWrapper(SectionDTO("sasha2", null)))
    }


}