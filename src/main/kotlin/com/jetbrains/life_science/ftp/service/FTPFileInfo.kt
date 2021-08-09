package com.jetbrains.life_science.ftp.service

import org.springframework.web.multipart.MultipartFile

interface FTPFileInfo {
    val id: Long
    val name: String
    val path: String
    val file: MultipartFile
}
