package com.jetbrains.life_science.user.group.service

interface FavoriteGroupInfo {
    val id: Long

    val name: String

    val parentId: Long?
}
