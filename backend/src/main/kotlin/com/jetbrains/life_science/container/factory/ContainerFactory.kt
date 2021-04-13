package com.jetbrains.life_science.container.factory

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.service.ContainerCreationInfo
import com.jetbrains.life_science.container.service.ContainerUpdateInfo
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.version.entity.MethodVersion
import org.springframework.stereotype.Component

@Component
class ContainerFactory {
    fun create(name: String, description: String, method: MethodVersion): Container {
        return Container(0, name, description, method)
    }

    fun setParams(container: Container, info: ContainerUpdateInfo) {
        container.name = info.name
        container.description = info.description
    }

    fun copy(container: Container): Container {
        return Container(0, container.name, container.description, container.method)
    }

    fun createByVersion(info: ContainerCreationInfo): Container {
        return Container(0, info.name, info.description, info.method)
    }
}
