package com.jetbrains.life_science.user.details.dto

import com.jetbrains.life_science.user.details.entity.FavouriteInfo
import com.jetbrains.life_science.user.details.entity.User

class FavouriteDataToInfoAdapter(
    override val user: User,
    override val articleId: Long
) : FavouriteInfo
