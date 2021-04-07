package com.jetbrains.life_science.container.factory

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.service.ContainerUpdateInfo
import com.jetbrains.life_science.method.entity.Method
import org.springframework.stereotype.Component

@Component
class ContainerFactory {
    fun create(name: String, description: String, method: Method): Container {
        return Container(0, name, description, method)
    }

    fun setParams(container: Container, info: ContainerUpdateInfo) {
        container.name = info.name
        container.description = info.description
    }
}
