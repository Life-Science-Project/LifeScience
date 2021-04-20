package com.jetbrains.life_science.article.section.parameter.factory

import com.jetbrains.life_science.article.section.parameter.entity.Parameter
import com.jetbrains.life_science.article.section.parameter.service.ParameterInfo
import org.springframework.stereotype.Component

@Component
class ParameterFactory {

    fun create(info: ParameterInfo): Parameter {
        return Parameter(info.id, info.name, info.defaultValue)
    }
}
