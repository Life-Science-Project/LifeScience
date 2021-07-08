package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.user.data.entity.UserPersonalData

interface DraftProtocolInfo {
    val id: Long

    val name: String

    val approach: PublicApproach

    val userData: UserPersonalData
}
