package com.jetbrains.life_science.container.dto

import com.jetbrains.life_science.container.service.ContainerUpdateInfo

class ContainerDTOToUpdateInfoAdapter(
    val dto: ContainerUpdateDTO
) : ContainerUpdateInfo {

    override val id: Long
        get() = dto.id

    override val name: String
        get() = dto.name

    override val description: String
        get() = dto.description
}
