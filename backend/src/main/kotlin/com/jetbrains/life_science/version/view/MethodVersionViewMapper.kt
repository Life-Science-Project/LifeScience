package com.jetbrains.life_science.version.view

import com.jetbrains.life_science.container.view.ContainerViewMapper
import com.jetbrains.life_science.version.entity.MethodVersion
import org.springframework.stereotype.Component

@Component
class MethodVersionViewMapper(
    val containerViewMapper: ContainerViewMapper
) {
    fun createView(methodVersion: MethodVersion): MethodVersionView {
        return MethodVersionView(
            methodVersion.name,
            methodVersion.mainMethod.id,
            methodVersion.containers
                .map { it.id }
        )
    }
}
