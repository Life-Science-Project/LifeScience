package com.jetbrains.life_science.section.dto

import javax.validation.constraints.NotBlank

class SectionDTO (
    @NotBlank
    val name: String,

    val parentID: Long?,

)