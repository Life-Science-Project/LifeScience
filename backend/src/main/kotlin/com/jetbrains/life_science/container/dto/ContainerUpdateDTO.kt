package com.jetbrains.life_science.container.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class ContainerUpdateDTO(

    @field:Positive
    val id: Long,

    @field:NotBlank
    val name: String,

    val description: String

)
