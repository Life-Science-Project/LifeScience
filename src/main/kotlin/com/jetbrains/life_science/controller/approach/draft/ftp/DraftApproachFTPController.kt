package com.jetbrains.life_science.controller.approach.draft.ftp

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.controller.approach.draft.ftp.dto.FTPFileDTOToInfoAdapter
import com.jetbrains.life_science.controller.approach.draft.ftp.view.FTPFileView
import com.jetbrains.life_science.controller.approach.draft.ftp.view.FTPFileViewMapper
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.exception.not_found.FTPFileNotFoundException
import com.jetbrains.life_science.ftp.service.FTPFileService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/approaches/draft/{approachId}/ftp")
class DraftApproachFTPController(
    val draftApproachService: DraftApproachService,
    val ftpFileService: FTPFileService,
    val mapper: FTPFileViewMapper
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
        val approach = draftApproachService.get(approachId)
        checkOwnerAccess(approach, credentials)
        val file = ftpFileService.getInfo(fileId)
        if (draftApproachService.hasFile(approachId, file)) {
            return ftpFileService.get(fileId)
        } else {
            throw FTPFileNotFoundException("File $fileId is not found")
        }
    }

    @PostMapping(value = ["/"])
    fun saveFile(
        @PathVariable approachId: Long,
        @RequestParam("file") file: MultipartFile,
        @AuthenticationPrincipal credentials: Credentials
    ): FTPFileView {
        val approach = draftApproachService.get(approachId)
        checkOwnerAccess(approach, credentials)
        val info = FTPFileDTOToInfoAdapter(file, "/approach/draft/${file.originalFilename}")
        val ftpFile = ftpFileService.create(info)
        draftApproachService.addFile(approachId, ftpFile)
        return mapper.toView(ftpFile)
    }

    fun checkOwnerAccess(approach: DraftApproach, credentials: Credentials) {
        if (approach.owner.id != credentials.id) {
            throw ForbiddenOperationException()
        }
    }
}
