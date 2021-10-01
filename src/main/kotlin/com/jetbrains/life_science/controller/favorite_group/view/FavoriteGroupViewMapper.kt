package com.jetbrains.life_science.controller.favorite_group.view

import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import org.springframework.stereotype.Component

@Component
class FavoriteGroupViewMapper {
    fun toShortViews(favoriteGroups: List<FavoriteGroup>): List<FavoriteGroupShortView> {
        return favoriteGroups.map { toShortView(it) }
    }

    fun toShortView(favoriteGroup: FavoriteGroup): FavoriteGroupShortView {
        return FavoriteGroupShortView(
            id = favoriteGroup.id,
            name = favoriteGroup.name
        )
    }
}
