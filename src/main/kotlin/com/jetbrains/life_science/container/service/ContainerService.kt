package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.container.entity.ContainerInfo

interface ContainerService {
    fun create(info: ContainerInfo)
}
