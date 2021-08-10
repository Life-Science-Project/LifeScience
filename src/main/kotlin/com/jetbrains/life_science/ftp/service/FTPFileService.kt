package com.jetbrains.life_science.ftp.service

import com.jetbrains.life_science.ftp.entity.FTPFile

interface FTPFileService {
    fun create(info: FTPFileInfo): FTPFile
    fun getInfo(id: Long): FTPFile
    fun get(id: Long): ByteArray
    fun delete(id: Long)
}
