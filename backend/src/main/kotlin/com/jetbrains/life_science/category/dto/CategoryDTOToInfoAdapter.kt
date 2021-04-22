package com.jetbrains.life_science.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo

class CategoryDTOToInfoAdapter(
    private val dto: CategoryDTO,
    override val id: Long = 0
) : CategoryInfo {

    override val name: String
        get() = dto.name

    override val parentId: Long?
        get() = dto.parentId

    override val order: Int
        get() = dto.order
}
