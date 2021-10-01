package com.jetbrains.life_science.controller.section.approach

import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/approaches/public/{approachId}/sections")
class PublicApproachSectionController(
    val publicApproachService: PublicApproachService,
    val sectionService: SectionService,
    val contentService: ContentService,
    val viewMapper: SectionViewMapper
) {

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable approachId: Long,
        @PathVariable sectionId: Long,
    ): SectionView {
        val section = getSectionSecured(approachId, sectionId)
        val content = contentService.findBySectionId(sectionId)
        return viewMapper.toView(section, content?.text)
    }

    private fun getSectionSecured(
        publicApproachId: Long,
        sectionId: Long
    ): Section {
        val section = sectionService.getById(sectionId)
        if (!publicApproachService.hasSection(publicApproachId, section)) {
            throw SectionNotFoundException(sectionId)
        }
        return section
    }
}
