package com.jetbrains.life_science.container.protocol.parameter.factory

import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import com.jetbrains.life_science.container.protocol.parameter.service.ProtocolParameterInfo
import org.springframework.stereotype.Component

@Component
class ProtocolParameterFactory {
    fun create(info: ProtocolParameterInfo): ProtocolParameter {
        return ProtocolParameter(
            id = info.id,
            name = info.name,
            value = info.value
        )
    }

    fun setParams(parameter: ProtocolParameter, info: ProtocolParameterInfo): ProtocolParameter {
        parameter.name = info.name
        parameter.value = info.value
        return parameter
    }
}
