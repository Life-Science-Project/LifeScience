package com.jetbrains.life_science.method.view

import com.jetbrains.life_science.method.entity.Method
import org.springframework.stereotype.Component

@Component
class MethodViewMapper {
    fun createView(method: Method): MethodView {
        return MethodView(method.id)
    }
}
