package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach

interface PublicApproachService {
    fun get(id: Long): PublicApproach

    fun create(approach: DraftApproach): PublicApproach
}
