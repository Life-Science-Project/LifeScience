package com.jetbrains.life_science.section

import com.jetbrains.life_science.section.dto.SectionDTO
import com.jetbrains.life_science.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.section.service.SectionServiceImpl
import com.jetbrains.life_science.section.view.SectionView
import com.jetbrains.life_science.section.view.SectionViewMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/section")
class SectionController(
    val sectionService: SectionServiceImpl,
    val sectionViewMapper: SectionViewMapper
) {
    @PostMapping("")
    fun addSection(@RequestBody @Valid sectionDTO: SectionDTO) {
        sectionService.addSection(SectionDTOToInfoAdapter(sectionDTO))
    }

    @GetMapping("/{id}")
    fun getChildren(@PathVariable id: Long): SectionView {
        val section = sectionService.getSection(id)
        val children = sectionService.getChildren(id)
        return sectionViewMapper.createView(section, children)
    }
}
