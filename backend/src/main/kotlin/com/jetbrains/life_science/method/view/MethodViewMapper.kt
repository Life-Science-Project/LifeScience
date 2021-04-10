package com.jetbrains.life_science.method.view

import com.jetbrains.life_science.container.view.ContainerViewMapper
import com.jetbrains.life_science.method.entity.Method
import org.springframework.stereotype.Component

@Component
class MethodViewMapper(
    val containerViewMapper: ContainerViewMapper
) {
    fun createView(method: Method): MethodView {
        return MethodView(
            method.name,
            method.section.id,
            containerViewMapper.createView(method.generalInfo),
            method.containers
                .map { it.id }
                .filter { it != method.generalInfo.id }
        )
    }
}
