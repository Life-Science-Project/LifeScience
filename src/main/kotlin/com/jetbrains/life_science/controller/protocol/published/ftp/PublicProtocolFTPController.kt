package com.jetbrains.life_science.controller.protocol.published.ftp

import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.exception.not_found.FTPFileNotFoundException
import com.jetbrains.life_science.ftp.service.FTPFileService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/protocols/public/{protocolId}/ftp")
class PublicProtocolFTPController(
    val publicProtocolService: PublicProtocolService,
    val ftpFileService: FTPFileService,
) {
    @GetMapping(
        value = ["/{fileId}"],
        produces = [MediaType.ALL_VALUE]
    )
    fun getFile(
        @PathVariable protocolId: Long,
        @PathVariable fileId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): ByteArray {
        val file = ftpFileService.getInfo(fileId)
        if (publicProtocolService.hasFile(protocolId, file)) {
            return ftpFileService.get(fileId)
        } else {
            throw FTPFileNotFoundException("File $fileId is not found")
        }
    }
}
