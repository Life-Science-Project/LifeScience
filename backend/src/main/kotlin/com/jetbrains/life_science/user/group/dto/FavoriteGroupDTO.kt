package com.jetbrains.life_science.user.group.dto

import javax.validation.constraints.NotBlank

data class FavoriteGroupDTO(

    @field:NotBlank
    var name: String,

    var parentId: Long?
)
