package com.jetbrains.life_science.ftp.factory

import com.jetbrains.life_science.ftp.entity.FTPFile
import com.jetbrains.life_science.ftp.service.FTPFileInfo
import org.springframework.stereotype.Component

@Component
class FTPFileFactory {
    fun create(info: FTPFileInfo): FTPFile {
        return FTPFile(
            id = info.id,
            name = info.file.originalFilename ?: "file",
            path = info.path,
            contentType = info.file.contentType ?: "text/txt"
        )
    }
}
