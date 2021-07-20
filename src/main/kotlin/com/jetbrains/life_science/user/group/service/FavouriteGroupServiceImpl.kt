package com.jetbrains.life_science.user.group.service

import com.jetbrains.life_science.exception.not_found.FavoriteGroupNotFoundException
import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import com.jetbrains.life_science.user.group.repository.FavoriteGroupRepository
import org.springframework.stereotype.Service

@Service
class FavouriteGroupServiceImpl(
    val favoriteGroupRepository: FavoriteGroupRepository
) : FavoriteGroupService {

    override fun getById(id: Long): FavoriteGroup {
        return favoriteGroupRepository.findById(id)
            .orElseThrow { FavoriteGroupNotFoundException("Favorite group not found by id $id") }
    }
}
