package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.util.interfaces.ContainsSections
import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section

interface PublicApproachService : ContainsSections {
    fun get(id: Long): PublicApproach

    fun create(approach: DraftApproach): PublicApproach

    override fun addSection(id: Long, section: Section)

    override fun removeSection(id: Long, section: Section)
}
