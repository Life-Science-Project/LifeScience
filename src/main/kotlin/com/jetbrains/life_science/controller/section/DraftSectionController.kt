package com.jetbrains.life_science.controller.section

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.controller.section.dto.SectionCreationDTO
import com.jetbrains.life_science.controller.section.dto.SectionCreationDTOToInfoAdapter
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/approaches/draft/{approachId}")
class DraftSectionController(
    val sectionService: SectionService,
    val draftApproachService: DraftApproachService,
    val viewMapper: SectionViewMapper,
    val contentVersionService: ContentVersionService
) {

    @GetMapping("/sections/{sectionId}")
    fun getSection(
        @PathVariable approachId: Long,
        @PathVariable sectionId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): SectionView {
        val section = getSectionSecured(approachId, sectionId, credentials)
        val content = contentVersionService.findBySectionId(sectionId)
        return viewMapper.toView(section, content?.text)
    }

    @PostMapping
    fun createSection(
        @PathVariable approachId: Long,
        @RequestBody dto: SectionCreationDTO,
        @AuthenticationPrincipal credentials: Credentials
    ): SectionView {
        val approach = getApproachSecured(approachId, credentials)
        val prevSection = dto.prevSectionId?.let { getSectionSecured(approach, it) }
        val info = SectionCreationDTOToInfoAdapter(dto, prevSection)
        val section = sectionService.create(info)
        return viewMapper.toView(section)
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
        if (!approach.hasParticipant(credentials)) {
            throw ForbiddenOperationException()
        }
        return approach
    }

    private fun getSectionSecured(
        draftApproach: DraftApproach,
        sectionId: Long
    ): Section {
        if (!draftApproach.hasSection(sectionId)) {
            throw SectionNotFoundException(sectionId)
        }
        return sectionService.getById(sectionId)
    }


}