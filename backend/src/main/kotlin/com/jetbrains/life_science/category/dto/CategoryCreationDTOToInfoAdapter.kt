package com.jetbrains.life_science.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo
import com.jetbrains.life_science.controller.category.dto.CategoryCreationDTO

class CategoryCreationDTOToInfoAdapter(
    dto: CategoryCreationDTO,
) : CategoryInfo {

    override val name = dto.name

    override val parentId = dto.initialParentId ?: 0L // to root category otherwise
}
