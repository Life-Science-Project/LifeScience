package com.jetbrains.life_science.user.group.factory

import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import com.jetbrains.life_science.user.group.service.FavoriteGroupInfo
import org.springframework.stereotype.Component

@Component
class FavoriteGroupFactory {
    fun createFavoriteGroup(info: FavoriteGroupInfo, parent: FavoriteGroup?): FavoriteGroup {
        return FavoriteGroup(
            id = 0,
            name = info.name,
            subGroups = mutableListOf(),
            protocols = mutableListOf(),
            approaches = mutableListOf(),
            parent = parent
        )
    }

    fun createDefaultFavoriteGroup(): FavoriteGroup {
        return FavoriteGroup(
            id = 0,
            name = "Favorite",
            subGroups = mutableListOf(),
            protocols = mutableListOf(),
            approaches = mutableListOf(),
            parent = null
        )
    }

    fun setParams(favoriteGroup: FavoriteGroup, info: FavoriteGroupInfo, parent: FavoriteGroup?) {
        favoriteGroup.name = info.name
        favoriteGroup.parent = parent
    }
}
