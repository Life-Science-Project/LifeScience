package com.jetbrains.life_science.controller.section.protocol

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.controller.section.dto.SectionCreationDTO
import com.jetbrains.life_science.controller.section.dto.SectionCreationDTOToInfoAdapter
import com.jetbrains.life_science.controller.section.dto.SectionDTO
import com.jetbrains.life_science.controller.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.exception.section.SectionAlreadyPublishedException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/protocols/draft/{protocolId}/sections")
class DraftProtocolSectionController(
    val sectionService: SectionService,
    val draftProtocolService: DraftProtocolService,
    val viewMapper: SectionViewMapper,
    val contentVersionService: ContentVersionService
) {

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable protocolId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): SectionView {
        val section = getSectionSecured(protocolId, sectionId, credentials)
        val content = contentVersionService.findBySectionId(sectionId)
        return viewMapper.toView(section, content?.text)
    }

    @PostMapping
    fun createSection(
        @PathVariable protocolId: Long,
        @RequestBody dto: SectionCreationDTO,
        @AuthenticationPrincipal credentials: Credentials
    ): SectionView {
        val protocol = getProtocolSecured(protocolId, credentials)
        val prevSection = dto.prevSectionId?.let { getSectionSecured(protocol, it) }
        val info = SectionCreationDTOToInfoAdapter(dto, prevSection, protocol.sections)
        val section = sectionService.create(info)
        draftProtocolService.addSection(protocolId, section)
        return viewMapper.toView(section)
    }

    @DeleteMapping("/{sectionId}")
    fun delete(
        @PathVariable protocolId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ) {
        val protocol = getProtocolSecured(protocolId, credentials)
        val section = sectionService.getById(sectionId)
        draftProtocolService.removeSection(protocolId, section)
        sectionService.deleteById(sectionId, protocol.sections)
    }

    @PatchMapping("/{sectionId}")
    fun updateSection(
        @PathVariable protocolId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials,
        @RequestBody dto: SectionDTO
    ): SectionView {
        val protocol = getProtocolSecured(protocolId, credentials)
        val section = getSectionSecured(protocol, sectionId)

        if (section.published) throw SectionAlreadyPublishedException()

        val prevSection = dto.prevSectionId?.let { getSectionSecured(protocol, it) }
        val info = SectionDTOToInfoAdapter(dto, protocol.sections, prevSection)
        val result = sectionService.update(section, info)

        return viewMapper.toView(result)
    }

    private fun getSectionSecured(
        draftApproachId: Long,
        sectionId: Long,
        credentials: Credentials
    ): Section {
        val protocol = getProtocolSecured(draftApproachId, credentials)
        return getSectionSecured(protocol, sectionId)
    }

    private fun getProtocolSecured(
        approachId: Long,
        credentials: Credentials
    ): DraftProtocol {
        val protocol = draftProtocolService.get(approachId)
        if (!draftProtocolService.hasParticipant(approachId, credentials)) {
            throw ForbiddenOperationException()
        }
        return protocol
    }

    private fun getSectionSecured(
        draftProtocol: DraftProtocol,
        sectionId: Long
    ): Section {
        val section = sectionService.getById(sectionId)
        if (!draftProtocolService.hasSection(draftProtocol.id, section)) {
            throw SectionNotFoundException(sectionId)
        }
        return sectionService.getById(sectionId)
    }
}
