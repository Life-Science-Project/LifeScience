package com.jetbrains.life_science.controller.section.dto

data class SectionCreationDTO(
    val name: String,
    val hidden: Boolean,
    val prevSectionId: Long? = null
)
