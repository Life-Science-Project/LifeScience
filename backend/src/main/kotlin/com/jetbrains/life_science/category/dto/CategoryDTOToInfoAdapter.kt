package com.jetbrains.life_science.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo

class CategoryDTOToInfoAdapter(
    private val categoryDTO: CategoryDTO,
    override val id: Long = 0
) : CategoryInfo {

    override val name: String
        get() = categoryDTO.name

    override val parentId: Long?
        get() = categoryDTO.parentId
}
