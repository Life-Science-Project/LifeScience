package com.jetbrains.life_science.controller.section.view

data class SectionView(
    val id: Long,
    val name: String,
    val hidden: Boolean,
    val content: String?
)
