package com.jetbrains.life_science.content.publish.service

interface ContentInfo : ContentCreationInfo {

    val id: String?

    val sectionId: Long
}
