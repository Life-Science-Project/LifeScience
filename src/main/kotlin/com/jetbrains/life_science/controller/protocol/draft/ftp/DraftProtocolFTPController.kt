package com.jetbrains.life_science.controller.protocol.draft.ftp

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.controller.ftp.dto.FTPFileDTOToInfoAdapter
import com.jetbrains.life_science.controller.ftp.view.FTPFileView
import com.jetbrains.life_science.controller.ftp.view.FTPFileViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.exception.not_found.FTPFileNotFoundException
import com.jetbrains.life_science.ftp.service.FTPFileService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/protocols/draft/{protocolId}/ftp")
class DraftProtocolFTPController(
    val draftProtocolService: DraftProtocolService,
    val ftpFileService: FTPFileService,
    val mapper: FTPFileViewMapper
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
        val protocol = draftProtocolService.get(protocolId)
        checkOwnerAccess(protocol, credentials)
        val file = ftpFileService.getInfo(fileId)
        if (draftProtocolService.hasFile(protocolId, file)) {
            return ftpFileService.get(fileId)
        } else {
            throw FTPFileNotFoundException("File $fileId is not found")
        }
    }

    @PostMapping("/")
    fun saveFile(
        @PathVariable protocolId: Long,
        @RequestParam("file") file: MultipartFile,
        @AuthenticationPrincipal credentials: Credentials
    ): FTPFileView {
        val protocol = draftProtocolService.get(protocolId)
        checkOwnerAccess(protocol, credentials)
        val info = FTPFileDTOToInfoAdapter(file, "/protocol/draft/${file.originalFilename}")
        val ftpFile = ftpFileService.create(info)
        draftProtocolService.addFile(protocolId, ftpFile)
        return mapper.toView(ftpFile)
    }

    @DeleteMapping("/{fileId}")
    fun deleteFile(
        @PathVariable protocolId: Long,
        @PathVariable fileId: Long,
        @AuthenticationPrincipal credentials: Credentials
    ) {
        val protocol = draftProtocolService.get(protocolId)
        checkOwnerAccess(protocol, credentials)
        val file = ftpFileService.getInfo(fileId)
        if (draftProtocolService.hasFile(protocolId, file)) {
            draftProtocolService.removeFile(protocolId, file)
            ftpFileService.delete(file.id)
        } else {
            throw FTPFileNotFoundException("File $fileId is not found")
        }
    }

    fun checkOwnerAccess(protocol: DraftProtocol, credentials: Credentials) {
        if (protocol.owner.id != credentials.id) {
            throw ForbiddenOperationException()
        }
    }
}
