package com.jetbrains.life_science.protocol.search.service

import com.jetbrains.life_science.protocol.entity.Protocol

interface ProtocolSearchUnitService {

    fun createSearchUnit(protocol: Protocol)

    fun deleteSearchUnitById(id: Long)

    fun update(protocol: Protocol)
}
