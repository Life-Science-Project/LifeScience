package com.jetbrains.life_science.version.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class MethodVersionDTO(

    @field:Positive
    val methodId: Long,

    @field:NotBlank
    val name: String

)
