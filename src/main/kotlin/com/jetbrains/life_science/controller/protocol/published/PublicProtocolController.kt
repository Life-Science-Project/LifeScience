package com.jetbrains.life_science.controller.protocol.published

import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.controller.protocol.published.view.PublicProtocolView
import com.jetbrains.life_science.controller.protocol.published.view.PublicProtocolViewMapper
import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/approaches/public/{approachId}/protocols")
class PublicProtocolController(
    val publicProtocolService: PublicProtocolService,
    val viewMapper: PublicProtocolViewMapper
) {

    @GetMapping("/{protocolId}")
    fun getProtocol(
        @PathVariable approachId: Long,
        @PathVariable protocolId: Long
    ): PublicProtocolView {
        val protocol = publicProtocolService.get(protocolId)
        if (protocol.approach.id != approachId) {
            throw ProtocolNotFoundException("Protocol not found")
        }
        return viewMapper.toView(protocol)
    }
}
