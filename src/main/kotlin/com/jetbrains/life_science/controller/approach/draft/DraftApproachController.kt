package com.jetbrains.life_science.controller.approach.draft

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.controller.approach.draft.dto.DraftApproachAddParticipantDTO
import com.jetbrains.life_science.controller.approach.draft.dto.DraftApproachCreationDTO
import com.jetbrains.life_science.controller.approach.draft.dto.DraftCategoryCreationDTOToInfoAdapter
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachView
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/approaches/draft")
class DraftApproachController(
    val draftApproachService: DraftApproachService,
    val viewMapper: DraftApproachViewMapper,
    val categoryService: CategoryService,
    val publicationRequestService: PublicApproachService,
    val credentialsService: CredentialsService
) {

    @GetMapping("/{approachId}")
    fun getApproach(@PathVariable approachId: Long): DraftApproachView {
        val approach = draftApproachService.get(approachId)
        return viewMapper.toView(approach)
    }

    @PostMapping
    fun create(
        @RequestBody dto: DraftApproachCreationDTO,
        @AuthenticationPrincipal author: Credentials
    ): DraftApproachView {
        val category = categoryService.getCategory(dto.initialCategoryId)
        val info = DraftCategoryCreationDTOToInfoAdapter(dto, category, author)
        val approach = draftApproachService.create(info)
        return viewMapper.toView(approach)
    }

    @PatchMapping("/{approachId}/send")
    fun sendToReview(
        @PathVariable approachId: Long,
        @AuthenticationPrincipal author: Credentials
    ) {
        val approach = draftApproachService.get(approachId)
        checkOwnerAccess(approach, author)
        publicationRequestService.create(approach)
    }

    @PostMapping("/{approachId}/participants")
    fun addParticipant(
        @PathVariable approachId: Long,
        @RequestBody dto: DraftApproachAddParticipantDTO,
        @AuthenticationPrincipal author: Credentials
    ) {
        val userCredentials = credentialsService.getByEmail(dto.email)
        val approach = draftApproachService.get(approachId)
        checkOwnerAccess(approach, author)
        draftApproachService.addParticipant(approach.id, userCredentials)
    }

    @DeleteMapping("/{approachId}/participants/{participantId}")
    fun deleteParticipant(
        @PathVariable approachId: Long,
        @PathVariable participantId: Long,
        @AuthenticationPrincipal author: Credentials
    ) {
        val approach = draftApproachService.get(approachId)
        checkOwnerOrAdminAccess(approach, author)
        val user = credentialsService.getById(participantId)
        draftApproachService.removeParticipant(approach.id, user)
    }

    fun checkOwnerOrAdminAccess(approach: DraftApproach, credentials: Credentials) {
        if (approach.owner.id != credentials.id && !credentials.isAdminOrModerator()) {
            throw ForbiddenOperationException()
        }
    }

    fun checkOwnerAccess(approach: DraftApproach, credentials: Credentials) {
        if (approach.owner.id != credentials.id) {
            throw ForbiddenOperationException()
        }
    }
}
