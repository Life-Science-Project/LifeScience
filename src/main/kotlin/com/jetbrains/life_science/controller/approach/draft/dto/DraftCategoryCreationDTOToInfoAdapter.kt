package com.jetbrains.life_science.controller.approach.draft.dto

import com.jetbrains.life_science.approach.service.DraftApproachInfo
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.credentials.entity.Credentials

class DraftCategoryCreationDTOToInfoAdapter(
    dto: DraftApproachCreationDTO,
    initialCategory: Category,
    override val owner: Credentials
) : DraftApproachInfo {
    override val id: Long = 0
    override val name: String = dto.name
    override val categories: List<Category> = listOf(initialCategory)
    override val tags: List<String> = listOf()
}
