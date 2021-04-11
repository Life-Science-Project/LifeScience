package com.jetbrains.life_science.method.search.factory

import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.search.MethodSearchUnit
import org.springframework.stereotype.Component

@Component
class MethodSearchUnitFactory {

    fun create(method: Method) = MethodSearchUnit(method.id, method.name)
}
