package com.jetbrains.life_science.method.factory

import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.entity.MethodInfo
import com.jetbrains.life_science.section.entity.Section
import org.springframework.context.MessageSource
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import java.util.*

@Component
class MethodFactory(
    val containerFactory: ContainerFactory,
    val messageSourceAccessor: MessageSourceAccessor
) {

    fun createMethod(info: MethodInfo, section: Section): Method {
        val name = messageSourceAccessor.getMessage("general_information")
        val method = Method(info.id, info.name, section)
        val generalInfoContainer = containerFactory.create(name, DEFAULT_DESCRIPTION, method)
        method.generalInfo = generalInfoContainer
        return method
    }

    companion object {
        const val DEFAULT_DESCRIPTION = "General information"
    }
}
