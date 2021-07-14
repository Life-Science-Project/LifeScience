package com.jetbrains.life_science.controller.approach.draft.dto

import com.jetbrains.life_science.approach.draft.service.DraftApproachCreationInfo
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.credentials.entity.Credentials

class DraftCategoryCreationDTOToInfoAdapter(
    dto: DraftApproachCreationDTO,
    override val parentCategory: Category,
    override val creator: Credentials
) : DraftApproachCreationInfo {

    override val name: String = dto.name
}
