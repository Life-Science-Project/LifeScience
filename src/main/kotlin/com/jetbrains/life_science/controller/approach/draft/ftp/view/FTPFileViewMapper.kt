package com.jetbrains.life_science.controller.approach.draft.ftp.view

import com.jetbrains.life_science.ftp.entity.FTPFile
import org.springframework.stereotype.Component

@Component
class FTPFileViewMapper {
    fun toView(file: FTPFile): FTPFileView {
        return FTPFileView(
            id = file.id,
            name = file.name,
            contentType = file.contentType
        )
    }
}
