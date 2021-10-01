package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface ProtocolInfo {
    val id: Long

    val name: String

    val approach: PublicApproach

    val owner: Credentials
}
