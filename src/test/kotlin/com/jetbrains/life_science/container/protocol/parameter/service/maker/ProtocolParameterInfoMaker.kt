package com.jetbrains.life_science.container.protocol.parameter.service.maker

import com.jetbrains.life_science.container.protocol.parameter.service.ProtocolParameterInfo

fun makeProtocolParameterInfo(
    id: Long,
    name: String,
    value: String
) = object : ProtocolParameterInfo {
    override val id = id
    override val name = name
    override val value = value
}
