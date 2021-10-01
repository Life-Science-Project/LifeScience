package com.jetbrains.life_science.controller.section.approach

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.service.DraftApproachService
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
@RequestMapping("/api/approaches/draft/{approachId}/sections")
class DraftApproachSectionController(
    val sectionService: SectionService,
    val draftApproachService: DraftApproachService,
    val viewMapper: SectionViewMapper,
    val contentVersionService: ContentVersionService
) {

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable approachId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): SectionView {
        val section = getSectionSecured(approachId, sectionId, credentials)
        val content = contentVersionService.findBySectionId(sectionId)
        return viewMapper.toView(section, content?.text)
    }

    @PostMapping("/many")
    fun createSections(
        @PathVariable approachId: Long,
        @RequestBody dto: List<SectionCreationDTO>,
        @AuthenticationPrincipal credentials: Credentials
    ): List<SectionView> {
        val approach = getApproachSecured(approachId, credentials)
        val sectionsInfo = dto.map { sectionDTO ->
            val prevSection = sectionDTO.prevSectionId?.let { getSectionSecured(approach, it) }
            SectionCreationDTOToInfoAdapter(sectionDTO, prevSection, approach.sections)
        }
        val createdSections = sectionService.createMany(sectionsInfo, approach.sections)
        createdSections.forEach { draftApproachService.addSection(approachId, it) }
        return viewMapper.toViewAll(createdSections)
    }

    @PostMapping
    fun createSection(
        @PathVariable approachId: Long,
        @RequestBody dto: SectionCreationDTO,
        @AuthenticationPrincipal credentials: Credentials
    ): SectionView {
        val approach = getApproachSecured(approachId, credentials)
        val prevSection = dto.prevSectionId?.let { getSectionSecured(approach, it) }
        val info = SectionCreationDTOToInfoAdapter(dto, prevSection, approach.sections)
        val section = sectionService.create(info)
        draftApproachService.addSection(approachId, section)
        return viewMapper.toView(section)
    }

    @DeleteMapping("/{sectionId}")
    fun delete(
        @PathVariable approachId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ) {
        val approach = getApproachSecured(approachId, credentials)
        val section = sectionService.getById(sectionId)
        draftApproachService.removeSection(approachId, section)
        sectionService.deleteById(sectionId, approach.sections)
    }

    @PatchMapping("/{sectionId}")
    fun updateSection(
        @PathVariable approachId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials,
        @RequestBody dto: SectionDTO
    ): SectionView {
        val approach = getApproachSecured(approachId, credentials)
        val section = getSectionSecured(approach, sectionId)

        if (section.published) throw SectionAlreadyPublishedException()

        val prevSection = dto.prevSectionId?.let { getSectionSecured(approach, it) }
        val info = SectionDTOToInfoAdapter(dto, approach.sections, prevSection)
        val result = sectionService.update(section, info)

        return viewMapper.toView(result)
    }

    private fun getSectionSecured(
        draftApproachId: Long,
        sectionId: Long,
        credentials: Credentials
    ): Section {
        val approach = getApproachSecured(draftApproachId, credentials)
        return getSectionSecured(approach, sectionId)
    }

    private fun getApproachSecured(
        approachId: Long,
        credentials: Credentials
    ): DraftApproach {
        val approach = draftApproachService.get(approachId)
        if (!draftApproachService.hasParticipant(approachId, credentials)) {
            throw ForbiddenOperationException()
        }
        return approach
    }

    private fun getSectionSecured(
        draftApproach: DraftApproach,
        sectionId: Long
    ): Section {
        val section = sectionService.getById(sectionId)
        if (!draftApproachService.hasSection(draftApproach.id, section)) {
            throw SectionNotFoundException(sectionId)
        }
        return section
    }
}
