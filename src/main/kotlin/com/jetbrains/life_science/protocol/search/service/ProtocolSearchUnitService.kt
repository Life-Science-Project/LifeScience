package com.jetbrains.life_science.protocol.search.service

import com.jetbrains.life_science.protocol.entity.PublicProtocol

interface ProtocolSearchUnitService {

    fun createSearchUnit(protocol: PublicProtocol)

    fun deleteSearchUnitById(id: Long)

    fun update(protocol: PublicProtocol)
}
