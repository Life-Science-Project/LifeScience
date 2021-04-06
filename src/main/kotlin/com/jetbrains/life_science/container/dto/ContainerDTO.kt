package com.jetbrains.life_science.container.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class ContainerDTO(

    @NotBlank
    val name: String,

    @Positive
    val methodId: Long,

    val description: String = ""
)
