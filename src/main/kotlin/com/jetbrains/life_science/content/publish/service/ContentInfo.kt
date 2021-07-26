package com.jetbrains.life_science.content.publish.service

interface ContentInfo {

    var sectionId: Long

    var text: String

    var references: List<String>

    var tags: List<String>
}
