package com.jetbrains.life_science.section.service.maker

import com.jetbrains.life_science.content.publish.service.ContentInfo
import com.jetbrains.life_science.section.service.SectionInfo

fun makeSectionInfo(
    id: Long,
    name: String,
    order: Long,
    visible: Boolean,
    contentInfo: ContentInfo
): SectionInfo = object : SectionInfo {
    override val id = id
    override var name = name
    override var order = order
    override var visible = visible
    override val contentInfo = contentInfo
}
