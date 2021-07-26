package com.jetbrains.life_science.controller.section.dto

data class SectionDTO(
    val name: String,
    val hidden: Boolean,
    val prevSectionId: Long? = null,
    val content: String
)
