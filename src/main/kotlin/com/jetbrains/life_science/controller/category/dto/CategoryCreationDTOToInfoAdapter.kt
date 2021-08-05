package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo

class CategoryCreationDTOToInfoAdapter(
    dto: CategoryCreationDTO,
) : CategoryInfo {

    override val name = dto.name

    override val aliases = dto.aliases.map { it.alias }

    override val parentId = dto.initialParentId
}
