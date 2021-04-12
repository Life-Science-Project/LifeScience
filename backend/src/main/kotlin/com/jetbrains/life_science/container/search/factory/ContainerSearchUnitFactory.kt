package com.jetbrains.life_science.container.search.factory

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.search.ContainerSearchUnit
import org.springframework.stereotype.Component

@Component
class ContainerSearchUnitFactory {

    fun create(container: Container) = ContainerSearchUnit(container.id, container.description ?: "")
}
