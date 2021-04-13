package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.version.entity.MethodVersion

interface ContainerService {

    fun createBlankByVersion(info: ContainerCreationInfo): Container

    fun create(info: ContainerInfo): Container

    fun deleteById(id: Long)

    fun getById(id: Long): Container

    fun checkExistsById(id: Long)

    fun update(into: ContainerUpdateInfo)

    fun createCopiesByMethod(method: MethodVersion, newMethod: MethodVersion)

    fun deleteSearchUnits(oldContainers: List<Container>)

    fun createSearchUnits(newContainers: List<Container>)
}
