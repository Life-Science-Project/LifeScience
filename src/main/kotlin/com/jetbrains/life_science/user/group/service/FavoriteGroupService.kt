package com.jetbrains.life_science.user.group.service

import com.jetbrains.life_science.user.group.entity.FavoriteGroup

interface FavoriteGroupService {
    fun getById(id: Long): FavoriteGroup

    fun createDefault(): FavoriteGroup
}
