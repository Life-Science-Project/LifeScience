package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.controller.protocol.view.ProtocolShortView
import com.jetbrains.life_science.controller.protocol.view.ProtocolViewMapper
import com.jetbrains.life_science.controller.user.view.UserFullView
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.service.UserPersonalDataService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    val userViewMapper: UserViewMapper,
    val publicProtocolService: PublicProtocolService,
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
    fun getPublicProtocols(
        @PathVariable userId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): List<ProtocolShortView> {
        checkAccess(userId, credentials)
        val publicProtocols = publicProtocolService.getAllByOwnerId(userId)
        return ProtocolViewMapper.toViewsShort(publicProtocols)
    }

    private fun checkAccess(userId: Long, credentials: Credentials) {
        if (!credentials.isAdminOrModerator() && userId != credentials.id) {
            throw ForbiddenOperationException()
        }
    }
}
