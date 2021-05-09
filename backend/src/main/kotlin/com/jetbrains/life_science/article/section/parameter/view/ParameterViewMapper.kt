package com.jetbrains.life_science.article.section.parameter.view

import com.jetbrains.life_science.article.section.parameter.entity.Parameter
import org.springframework.stereotype.Component

@Component
class ParameterViewMapper {
    fun createView(parameter: Parameter): ParameterView {
        return ParameterView(
            id = parameter.id,
            name = parameter.name,
            defaultValue = parameter.defaultValue
        )
    }

    fun createViews(parameters: List<Parameter>): List<ParameterView> {
        return parameters.map { createView(it) }
    }
}
