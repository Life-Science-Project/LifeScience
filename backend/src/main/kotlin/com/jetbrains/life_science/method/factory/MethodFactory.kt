package com.jetbrains.life_science.method.factory

import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.service.MethodInfo
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class MethodFactory {

    fun create(section: Section): Method {
        return Method(0, section)
    }

}
