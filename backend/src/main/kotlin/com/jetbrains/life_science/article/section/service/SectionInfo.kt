package com.jetbrains.life_science.article.section.service

import com.jetbrains.life_science.article.section.parameter.service.ParameterInfo

interface SectionInfo {

    val id: Long

    val name: String

    val description: String

    val articleVersionId: Long

    val parameters: List<ParameterInfo>

    val order: Int

    val visible: Boolean
}
