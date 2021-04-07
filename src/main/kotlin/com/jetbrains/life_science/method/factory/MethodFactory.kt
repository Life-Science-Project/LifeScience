package com.jetbrains.life_science.method.factory

import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.service.MethodInfo
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class MethodFactory(
    val containerFactory: ContainerFactory
) {

    private val generalInfo = "General information"

    fun createMethod(info: MethodInfo, section: Section): Method {
        val method = Method(info.id, info.name, section)
        val generalInfoContainer = containerFactory.create(info.name, generalInfo, method)
        method.generalInfo = generalInfoContainer
        return method
    }
}
