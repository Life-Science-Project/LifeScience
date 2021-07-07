package com.jetbrains.life_science.user.group.dto

import com.jetbrains.life_science.user.group.service.FavoriteGroupInfo

class FavoriteGroupDTOToInfoAdapter(
    dto: FavoriteGroupDTO,
    override val id: Long = 0
) : FavoriteGroupInfo {

    override val name = dto.name

    override val parentId = dto.parentId
}
