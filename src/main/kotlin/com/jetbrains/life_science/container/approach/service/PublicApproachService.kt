package com.jetbrains.life_science.container.approach.service

import com.jetbrains.life_science.container.ContainsSections
import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.ftp.entity.FTPFile
import com.jetbrains.life_science.section.entity.Section

interface PublicApproachService : ContainsSections {
    fun get(id: Long): PublicApproach

    fun create(approach: DraftApproach): PublicApproach

    fun hasFile(id: Long, file: FTPFile): Boolean

    override fun addSection(id: Long, section: Section)

    override fun removeSection(id: Long, section: Section)
}
