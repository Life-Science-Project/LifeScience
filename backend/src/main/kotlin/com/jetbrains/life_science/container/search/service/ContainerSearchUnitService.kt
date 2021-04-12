package com.jetbrains.life_science.container.search.service

import com.jetbrains.life_science.container.entity.Container

interface ContainerSearchUnitService {

    fun create(container: Container)

    fun delete(id: Long)

    fun update(container: Container)
}
