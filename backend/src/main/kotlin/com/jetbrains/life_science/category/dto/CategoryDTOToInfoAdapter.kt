package com.jetbrains.life_science.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo

class CategoryDTOToInfoAdapter(
    dto: CategoryDTO,
    override val id: Long = 0
) : CategoryInfo {

    override val name = dto.name

    override val parentId = dto.parentId

    override val order = dto.order
}
