package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.protocol.entity.PublicProtocol

interface PublicProtocolService {
    fun get(id: Long): PublicProtocol

    fun create(info: PublicProtocolInfo): PublicProtocol
}
