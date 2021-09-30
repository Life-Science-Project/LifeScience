package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.approach.view.ApproachViewMapper
import com.jetbrains.life_science.controller.protocol.view.ProtocolView
import com.jetbrains.life_science.controller.protocol.view.ProtocolViewMapper
import com.jetbrains.life_science.controller.user.dto.UserPersonalDataDTO
import com.jetbrains.life_science.controller.user.dto.UserPersonalDataDTOToInfoAdapter
import com.jetbrains.life_science.controller.user.view.UserFullView
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.service.UserPersonalDataService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    val userViewMapper: UserViewMapper,
    val protocolViewMapper: ProtocolViewMapper,
    val approachViewMapper: ApproachViewMapper,
    val draftProtocolService: DraftProtocolService,
    val publicProtocolService: PublicProtocolService,
    val draftApproachService: DraftApproachService,
    val publicApproachService: PublicApproachService,
    val userPersonalDataService: UserPersonalDataService
) {

    @GetMapping("/current")
    @Transactional(readOnly = true)
    fun getCurrentUser(
        @AuthenticationPrincipal credentials: Credentials
    ): UserFullView {
        val userData = userPersonalDataService.getByCredentials(credentials)
        return userViewMapper.toFullView(credentials, userData)
    }

    @GetMapping("/{userId}/protocols/public")
    @Transactional(readOnly = true)
    fun getPublicProtocols(
        @PathVariable userId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): List<ProtocolView> {
        checkAccess(userId, credentials)
        val publicProtocols = publicProtocolService.getAllByOwnerId(userId)
        return protocolViewMapper.toViews(publicProtocols)
    }

    @GetMapping("/{userId}/protocols/draft")
    @Transactional(readOnly = true)
    fun getDraftProtocols(
        @PathVariable userId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): List<ProtocolView> {
        checkAccess(userId, credentials)
        val draftProtocols = draftProtocolService.getAllByOwnerId(userId)
        return protocolViewMapper.toViews(draftProtocols)
    }

    @GetMapping("/{userId}/approaches/public")
    @Transactional(readOnly = true)
    fun getPublicApproaches(
        @PathVariable userId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): List<ApproachShortView> {
        checkAccess(userId, credentials)
        val publicApproach = publicApproachService.getAllByOwnerId(userId)
        return approachViewMapper.toViewsShort(publicApproach)
    }

    @GetMapping("/{userId}/approaches/draft")
    @Transactional(readOnly = true)
    fun getDraftApproaches(
        @PathVariable userId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): List<ApproachShortView> {
        checkAccess(userId, credentials)
        val draftApproach = draftApproachService.getAllByOwnerId(userId)
        return approachViewMapper.toViewsShort(draftApproach)
    }

    @PatchMapping("/{userId}/data")
    fun updatePersonalData(
        @PathVariable userId: Long,
        @AuthenticationPrincipal credentials: Credentials,
        @RequestBody dto: UserPersonalDataDTO
    ): UserFullView {
        checkAccess(userId, credentials)
        val info = UserPersonalDataDTOToInfoAdapter(dto)
        val currentPersonalData = userPersonalDataService.getById(userId)
        val personalData = userPersonalDataService.update(info, currentPersonalData)
        return userViewMapper.toFullView(credentials, personalData)
    }

    private fun checkAccess(userId: Long, credentials: Credentials) {
        if (!credentials.isAdminOrModerator() && userId != credentials.id) {
            throw ForbiddenOperationException()
        }
    }
}
