package com.jetbrains.life_science.controller.approach.draft.dto

import com.jetbrains.life_science.container.approach.service.DraftApproachInfo
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.credentials.entity.Credentials

class DraftApproachDTOToInfoAdapter(
    dto: DraftApproachDTO,
    initialCategory: Category,
    override val owner: Credentials
) : DraftApproachInfo {
    override val id: Long = 0
    override val name: String = dto.name
    override val aliases: List<String> = mutableListOf()
    override val categories: List<Category> = listOf(initialCategory)
    override val tags: List<String> = listOf()
}
