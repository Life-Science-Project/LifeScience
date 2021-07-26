package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section

interface PublicApproachService {
    fun get(id: Long): PublicApproach

    fun create(approach: DraftApproach): PublicApproach

    fun addSection(approachId: Long, section: Section): PublicApproach

    fun removeSection(approachId: Long, section: Section): PublicApproach
}
