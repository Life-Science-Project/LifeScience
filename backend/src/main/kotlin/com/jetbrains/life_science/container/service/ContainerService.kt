package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.container.entity.Container

interface ContainerService {

    fun create(info: ContainerInfo): Container

    fun deleteById(id: Long)

    /**
     * Cleans the contents of the container before removing it
     */
    fun initDeletion(containerId: Container)

    fun checkExistsById(id: Long)

    fun update(into: ContainerUpdateInfo)

    /**
     * Creates a search unit for container and an empty article, after it was stored id database
     */
    fun completeCreationGeneralInfo(container: Container)
}
