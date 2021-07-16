package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.category.service.CategoryUpdateInfo

class CategoryUpdateDTOToInfoAdapter(
    dto: CategoryUpdateDTO,
    override val id: Long
) : CategoryUpdateInfo {

    override val name: String = dto.name

    override val aliases = dto.aliases.map { it.alias }

    override val parentsToAddIds: List<Long> = dto.parentsToAdd

    override val parentsToDeleteIds: List<Long> = dto.parentsToDelete
}
