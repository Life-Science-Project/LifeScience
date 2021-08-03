package com.jetbrains.life_science.container.protocol.parameter.service

import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter

interface ProtocolParameterService {
    fun create(info: ProtocolParameterInfo): ProtocolParameter
    fun get(id: Long): ProtocolParameter
    fun update(info: ProtocolParameterInfo): ProtocolParameter
    fun delete(id: Long)
}
