package com.jetbrains.life_science.version.search.factory

import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.search.MethodVersionSearchUnit
import org.springframework.stereotype.Component

@Component
class MethodSearchUnitFactory {

    fun create(methodVersion: MethodVersion): MethodVersionSearchUnit {
        return MethodVersionSearchUnit(methodVersion.id, methodVersion.name)
    }
}
