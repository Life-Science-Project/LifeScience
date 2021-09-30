package com.jetbrains.life_science.controller.section.protocol

import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/approaches/public/{approachId}/protocols/{protocolId}/sections")
class PublicProtocolSectionController(
    val publicProtocolService: PublicProtocolService,
    val sectionService: SectionService,
    val contentService: ContentService,
    val viewMapper: SectionViewMapper
) {

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable approachId: Long,
        @PathVariable protocolId: Long,
        @PathVariable sectionId: Long,
    ): SectionView {
        val section = getSectionSecured(approachId, protocolId, sectionId)
        val content = contentService.findBySectionId(sectionId)
        return viewMapper.toView(section, content?.text)
    }

    private fun getSectionSecured(
        approachId: Long,
        publicProtocolId: Long,
        sectionId: Long
    ): Section {
        if (!publicProtocolService.isInApproach(publicProtocolId, approachId)) {
            throw ProtocolNotFoundException("Not found protocol: $publicProtocolId")
        }
        val section = sectionService.getById(sectionId)
        if (!publicProtocolService.hasSection(publicProtocolId, section)) {
            throw SectionNotFoundException(sectionId)
        }
        return section
    }
}
