package com.jetbrains.life_science.controller.section.dto

data class SectionDTO(
    val name: String,
    val hidden: Boolean,
    val content: String,
    val prevSectionId: Long? = null
)
