package com.jetbrains.life_science.ftp.repository

import com.jetbrains.life_science.ftp.entity.FTPFile
import org.springframework.data.jpa.repository.JpaRepository

interface FTPFileRepository : JpaRepository<FTPFile, Long> {
    fun existsByFilePath(path: String): Boolean
}
