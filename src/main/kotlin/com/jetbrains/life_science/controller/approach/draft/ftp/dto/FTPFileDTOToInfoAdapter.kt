package com.jetbrains.life_science.controller.approach.draft.ftp.dto

import com.jetbrains.life_science.ftp.service.FTPFileInfo
import org.springframework.web.multipart.MultipartFile

class FTPFileDTOToInfoAdapter(
    override val file: MultipartFile,
    override val path: String
) : FTPFileInfo {
    override val id: Long = 0
}
