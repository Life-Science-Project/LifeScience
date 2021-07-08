package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.content.publish.service.ContentCreationInfo

interface SectionInfo {
    val id: Long

    var name: String

    var order: Long

    var visible: Boolean

    val contentInfo: ContentCreationInfo?
}
