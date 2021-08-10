package com.jetbrains.life_science.controller.approach.published.ftp

import com.jetbrains.life_science.container.approach.service.PublicApproachService
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
@RequestMapping("/api/approaches/public/{approachId}/ftp")
class PublicApproachFTPController(
    val publicApproachService: PublicApproachService,
    val ftpFileService: FTPFileService,
) {
    @GetMapping(
        value = ["/{fileId}"],
        produces = [MediaType.ALL_VALUE]
    )
    fun getFile(
        @PathVariable approachId: Long,
        @PathVariable fileId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ): ByteArray {
        val file = ftpFileService.getInfo(fileId)
        if (publicApproachService.hasFile(approachId, file)) {
            return ftpFileService.get(fileId)
        } else {
            throw FTPFileNotFoundException("File $fileId is not found")
        }
    }
}
