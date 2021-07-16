package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.PublicApproach

interface PublicApproachService {
    fun get(id: Long): PublicApproach

    fun create(info: PublicApproachInfo): PublicApproach
}
