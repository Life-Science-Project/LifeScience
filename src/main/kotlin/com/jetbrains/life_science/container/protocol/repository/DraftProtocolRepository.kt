package com.jetbrains.life_science.container.protocol.repository

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.ftp.entity.FTPFile
import com.jetbrains.life_science.section.entity.Section
import org.springframework.data.jpa.repository.JpaRepository

interface DraftProtocolRepository : JpaRepository<DraftProtocol, Long> {
    fun existsByIdAndSectionsContains(id: Long, section: Section): Boolean
    fun existsByIdAndFilesContains(id: Long, file: FTPFile): Boolean
}
