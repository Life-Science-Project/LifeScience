package com.jetbrains.life_science.container.search.service

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.search.ContainerSearchUnit

interface ContainerSearchUnitService {

    fun create(container: Container): ContainerSearchUnit

    fun delete(id: Long)

    fun update(container: Container)

}
