package com.jetbrains.life_science.approach.draft.service.maker

import com.jetbrains.life_science.approach.service.DraftApproachInfo
import com.jetbrains.life_science.category.entity.Category

fun makeDraftApproachInfo(
    id: Long,
    name: String,
    tags: List<String>,
    categories: List<Category>
): DraftApproachInfo = object : DraftApproachInfo {
    override val id = id
    override val name = name
    override val tags = tags
    override val categories = categories
}
