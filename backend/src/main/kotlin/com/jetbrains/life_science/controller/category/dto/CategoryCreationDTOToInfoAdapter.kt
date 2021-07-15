package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo

class CategoryCreationDTOToInfoAdapter(
    dto: CategoryCreationDTO,
) : CategoryInfo {

    override val name = dto.name

    override val aliases = dto.aliases

    override val parentId = dto.initialParentId ?: 0L // to root category otherwise
}
