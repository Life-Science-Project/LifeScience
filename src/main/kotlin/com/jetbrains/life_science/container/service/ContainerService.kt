package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.entity.ContainerInfo

interface ContainerService {

    fun create(info: ContainerInfo): Container

    fun deleteById(id: Long)

    fun prepareDeletionById(containerId: Long)

    fun checkExistsById(id: Long)
}
