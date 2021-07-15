package com.jetbrains.life_science.user.group.factory

import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import com.jetbrains.life_science.user.group.service.FavoriteGroupInfo

class FavoriteGroupFactory {
    fun createFavouriteGroup(info: FavoriteGroupInfo, parent: FavoriteGroup?): FavoriteGroup {
        return FavoriteGroup(
            id = info.id,
            name = info.name,
            subGroups = mutableListOf(),
            protocols = mutableListOf(),
            approaches = mutableListOf(),
            parent = parent
        )
    }

    fun setParams(favoriteGroup: FavoriteGroup, info: FavoriteGroupInfo, parent: FavoriteGroup?) {
        favoriteGroup.name = info.name
        favoriteGroup.parent = parent
    }
}
