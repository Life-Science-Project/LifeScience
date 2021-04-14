package com.jetbrains.life_science.content.dto

import javax.validation.constraints.Positive

class ContentCreationDTO(

    @field:Positive
    val sectionId: Long,

    val text: String,

    val references: MutableList<String>,

    val tags: MutableList<String>
)
