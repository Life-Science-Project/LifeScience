package com.jetbrains.life_science.article.content.dto

import javax.validation.constraints.NotBlank

data class ContentUpdateDTO(

    @field:NotBlank
    val id: String,

    val text: String
)
