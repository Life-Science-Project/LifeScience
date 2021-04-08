package com.jetbrains.life_science.container.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class ContainerDTO(

    @field:NotBlank
    val name: String,

    @field:Positive
    val methodId: Long,

    val description: String = ""
)
