package com.jetbrains.life_science.controller.approach.draft

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.controller.approach.draft.dto.DraftApproachAddParticipantDTO
import com.jetbrains.life_science.controller.approach.draft.dto.DraftApproachDTO
import com.jetbrains.life_science.controller.approach.draft.dto.DraftApproachDTOToInfoAdapter
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachView
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestInfo
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import com.jetbrains.life_science.user.data.service.UserPersonalDataService
import com.jetbrains.life_science.util.UTCZone
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/approaches/draft")
class DraftApproachController(
    val draftApproachService: DraftApproachService,
    val viewMapper: DraftApproachViewMapper,
    val categoryService: CategoryService,
    val publishApproachRequestService: PublishApproachRequestService,
    val credentialsService: CredentialsService,
    val userPersonalDataService: UserPersonalDataService
) {

    @GetMapping("/{approachId}")
    fun getApproach(
        @PathVariable approachId: Long,
        @AuthenticationPrincipal user: Credentials
    ): DraftApproachView {
        val approach = draftApproachService.get(approachId)
        checkDraftApproachAccess(approach, user)
        return viewMapper.toView(
            draftApproach = approach,
            usersData = extractUsersData(approach)
        )
    }

    @PostMapping
    fun create(
        @RequestBody dto: DraftApproachDTO,
        @AuthenticationPrincipal author: Credentials
    ): DraftApproachView {
        val category = categoryService.getCategory(dto.initialCategoryId)
        val info = DraftApproachDTOToInfoAdapter(dto, category, author)
        val approach = draftApproachService.create(info)
        return viewMapper.toView(
            draftApproach = approach,
            usersData = extractUsersData(approach)
        )
    }

    @PatchMapping("/{approachId}/send")
    fun sendToReview(
        @PathVariable approachId: Long,
        @AuthenticationPrincipal author: Credentials
    ) {
        val approach = draftApproachService.get(approachId)
        checkOwnerOrAdminAccess(approach, author)
        val publicationInfo = DraftApproachToPublicationRequestAdapter(author, approach)
        publishApproachRequestService.create(publicationInfo)
    }

    @PostMapping("/{approachId}/participants")
    fun addParticipant(
        @PathVariable approachId: Long,
        @RequestBody dto: DraftApproachAddParticipantDTO,
        @AuthenticationPrincipal author: Credentials
    ) {
        val userCredentials = credentialsService.getByEmail(dto.email)
        val approach = draftApproachService.get(approachId)
        checkOwnerOrAdminAccess(approach, author)
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

    private fun extractUsersData(approach: DraftApproach) =
        approach.participants.map { userPersonalDataService.getByCredentials(it) }

    private fun checkDraftApproachAccess(approach: DraftApproach, credentials: Credentials) {
        if (approach.participants.all { it.id != credentials.id } && !credentials.isAdminOrModerator()) {
            throw ForbiddenOperationException()
        }
    }

    private fun checkOwnerOrAdminAccess(approach: DraftApproach, credentials: Credentials) {
        if (approach.owner.id != credentials.id && !credentials.isAdminOrModerator()) {
            throw ForbiddenOperationException()
        }
    }

    private class DraftApproachToPublicationRequestAdapter(
        override val editor: Credentials,
        override val approach: DraftApproach
    ) : PublishApproachRequestInfo {
        override val id: Long = 0
        override val date: LocalDateTime = LocalDateTime.now(UTCZone)
    }
}
