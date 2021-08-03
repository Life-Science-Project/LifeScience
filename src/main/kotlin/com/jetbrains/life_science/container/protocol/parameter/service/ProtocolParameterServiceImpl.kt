package com.jetbrains.life_science.container.protocol.parameter.service

import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import com.jetbrains.life_science.container.protocol.parameter.factory.ProtocolParameterFactory
import com.jetbrains.life_science.container.protocol.parameter.repository.ProtocolParameterRepository
import com.jetbrains.life_science.exception.not_found.ProtocolParameterNotFoundException
import org.springframework.stereotype.Service

@Service
class ProtocolParameterServiceImpl(
    val repository: ProtocolParameterRepository,
    val factory: ProtocolParameterFactory
) : ProtocolParameterService {
    override fun create(info: ProtocolParameterInfo): ProtocolParameter {
        val parameter = factory.create(info)
        return repository.save(parameter)
    }

    override fun get(id: Long): ProtocolParameter {
        return repository.findById(id).orElseThrow {
            ProtocolParameterNotFoundException("Parameter with id $id is not found")
        }
    }

    override fun update(info: ProtocolParameterInfo): ProtocolParameter {
        val parameter = get(info.id)
        factory.setParams(parameter, info)
        return repository.save(parameter)
    }

    override fun delete(id: Long) {
        if (!repository.existsById(id)) {
            throw ProtocolParameterNotFoundException("Parameter with id $id is not found")
        }
        repository.deleteById(id)
    }
}
