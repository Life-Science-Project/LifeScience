package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.entity.ContainerInfo

interface ContainerService {

    fun create(info: ContainerInfo): Container

    fun existsById(id: Long): Boolean

    fun delete(id: Long)

    fun checkExistsById(id: Long)
}
