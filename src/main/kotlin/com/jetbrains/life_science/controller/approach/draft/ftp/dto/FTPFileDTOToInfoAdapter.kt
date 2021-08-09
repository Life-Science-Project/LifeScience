package com.jetbrains.life_science.controller.approach.draft.ftp.dto

import com.jetbrains.life_science.ftp.service.FTPFileInfo
import org.springframework.web.multipart.MultipartFile

class FTPFileDTOToInfoAdapter(
    dto: FTPFileDTO,
    override val file: MultipartFile
) : FTPFileInfo {
    override val id: Long = 0
    override val name = dto.name
    override val path = dto.path
}
