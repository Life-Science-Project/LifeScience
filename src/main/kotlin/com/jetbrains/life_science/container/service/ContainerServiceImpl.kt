package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.container.entity.ContainerInfo
import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.container.repository.ContainerRepository
import com.jetbrains.life_science.method.service.MethodService
import org.springframework.stereotype.Service

@Service
class ContainerServiceImpl(
    val containerFactory: ContainerFactory,
    val containerRepository: ContainerRepository,
    val methodService: MethodService
) : ContainerService {


    override fun create(info: ContainerInfo) {
        val method = methodService.getMethod(info.id)
        val container = containerFactory.create(info.name, info.description, method)
        containerRepository.save(container)
    }
}
