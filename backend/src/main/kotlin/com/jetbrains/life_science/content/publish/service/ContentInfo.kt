package com.jetbrains.life_science.content.publish.service

interface ContentInfo {

    val id: String?

    var sectionId: Long

    var text: String

    var references: List<String>

    var tags: List<String>
}
