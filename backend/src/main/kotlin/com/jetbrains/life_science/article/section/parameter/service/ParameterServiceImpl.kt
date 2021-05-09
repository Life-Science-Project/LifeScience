package com.jetbrains.life_science.article.section.parameter.service

import com.jetbrains.life_science.article.section.parameter.entity.Parameter
import com.jetbrains.life_science.article.section.parameter.factory.ParameterFactory
import com.jetbrains.life_science.article.section.parameter.repository.ParameterRepository
import org.springframework.stereotype.Service

@Service
class ParameterServiceImpl(
    val repository: ParameterRepository,
    val factory: ParameterFactory
) : ParameterService {
    override fun create(info: ParameterInfo): Parameter {
        var parameter = factory.create(info)
        parameter = repository.save(parameter)
        return parameter
    }
}
