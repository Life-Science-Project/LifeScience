package com.jetbrains.life_science.container.dto

data class ContainerDTO(
    val name: String,
    val methodId: Long,
    val description: String = ""
)
