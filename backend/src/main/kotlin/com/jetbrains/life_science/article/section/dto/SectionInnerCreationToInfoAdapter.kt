package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.content.publish.service.ContentCreationInfo
import com.jetbrains.life_science.article.section.service.SectionCreationInfo
import com.jetbrains.life_science.article.section.service.SectionInfo

class SectionInnerCreationToInfoAdapter(
    override val articleVersionId: Long,
    override val order: Int,
    info: SectionCreationInfo,
    override val id: Long = 0,
) : SectionInfo {

    override val name = info.name

    override val description = info.description

    override val visible = info.visible

    override val contentInfo: ContentCreationInfo? = info.contentCreationInfo
}
