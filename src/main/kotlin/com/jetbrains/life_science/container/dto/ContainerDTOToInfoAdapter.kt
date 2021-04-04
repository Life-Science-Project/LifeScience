package com.jetbrains.life_science.container.dto

import com.jetbrains.life_science.container.entity.ContainerInfo

class ContainerDTOToInfoAdapter(
    private val dto: ContainerDTO
) : ContainerInfo {

    override val id: Long
        get() = 0

    override val name: String
        get() = dto.name

    override val description: String
        get() = dto.description

    override val methodId: Long
        get() = dto.methodId

}
