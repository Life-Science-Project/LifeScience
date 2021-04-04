package com.jetbrains.life_science.method.factory

import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.entity.MethodInfo
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class MethodFactory(
    val containerFactory: ContainerFactory
) {


    fun createMethod(info: MethodInfo, section: Section): Method {
        val method = Method(info.getId(), info.getName(), section)
        val generalInfoContainer = containerFactory.create(info.getName(), DEFAULT_DESCRIPTION, method)
        method.generalInfo = generalInfoContainer
        return method
    }

    companion object {
        const val DEFAULT_DESCRIPTION = "General information"
    }
}
