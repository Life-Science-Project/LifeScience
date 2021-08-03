package com.jetbrains.life_science.controller.protocol.parameter.view

import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import org.springframework.stereotype.Component

@Component
class ProtocolParameterViewMapper {
    fun toView(parameter: ProtocolParameter): ProtocolParameterView {
        return ProtocolParameterView(
            name = parameter.name,
            value = parameter.value
        )
    }

    fun toViewAll(parameters: List<ProtocolParameter>): List<ProtocolParameterView> {
        return parameters.map { toView(it) }
    }
}
