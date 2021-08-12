package com.jetbrains.life_science.controller.review.request

import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.controller.review.request.dto.DraftApproachToPublicationRequestAdapter
import com.jetbrains.life_science.controller.review.request.dto.DraftProtocolToPublicationRequestAdapter
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestService
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/review/request")
class ReviewRequestController(
    val draftApproachService: DraftApproachService,
    val publishApproachRequestService: PublishApproachRequestService,
    val draftProtocolService: DraftProtocolService,
    val publishProtocolRequestService: PublishProtocolRequestService
) {

    @PostMapping("/approaches/draft/{approachId}")
    fun draftApproachToReview(
        @PathVariable approachId: Long,
        @AuthenticationPrincipal author: Credentials
    ) {
        val approach = draftApproachService.get(approachId)
        checkOwnerOrAdminAccess(approach.owner, author)
        val publicationInfo = DraftApproachToPublicationRequestAdapter(author, approach)
        publishApproachRequestService.create(publicationInfo)
    }

    @PostMapping("/protocols/draft/{protocolId}")
    fun draftProtocolToReview(
        @PathVariable protocolId: Long,
        @AuthenticationPrincipal author: Credentials
    ) {
        val protocol = draftProtocolService.get(protocolId)
        checkOwnerOrAdminAccess(protocol.owner, author)
        val publicationInfo = DraftProtocolToPublicationRequestAdapter(author, protocol)
        publishProtocolRequestService.create(publicationInfo)
    }

    fun checkOwnerOrAdminAccess(owner: Credentials, credentials: Credentials) {
        if (owner.id != credentials.id && !credentials.isAdminOrModerator()) {
            throw ForbiddenOperationException()
        }
    }
}
