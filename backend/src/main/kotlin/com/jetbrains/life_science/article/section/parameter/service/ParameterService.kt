package com.jetbrains.life_science.article.section.parameter.service

import com.jetbrains.life_science.article.section.parameter.entity.Parameter

interface ParameterService {
    fun create(info: ParameterInfo): Parameter
}
