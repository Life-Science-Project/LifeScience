package com.jetbrains.life_science.section.controller

import com.jetbrains.life_science.section.dto.SectionDTO
import com.jetbrains.life_science.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.section.dto.SectionDTOToUpdateInfoAdapter
import com.jetbrains.life_science.section.dto.SectionUpdateDTO
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.section.view.SectionView
import com.jetbrains.life_science.section.view.SectionViewMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sections")
class SectionController(
    val service: SectionService,
    val sectionViewMapper: SectionViewMapper
) {

    @PostMapping
    fun create(@Validated @RequestBody dto: SectionDTO) {
        service.create(SectionDTOToInfoAdapter(dto))
    }

    @GetMapping("/{id}")
    fun getContainer(@PathVariable id: Long): SectionView {
        return sectionViewMapper.createView(service.getById(id))
    }

    @PutMapping
    fun updateContainer(@Validated @RequestBody dto: SectionUpdateDTO) {
        service.update(SectionDTOToUpdateInfoAdapter(dto))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.deleteById(id)
    }
}
