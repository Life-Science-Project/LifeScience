package com.jetbrains.life_science.container.approach.draft.service.maker

import com.jetbrains.life_science.container.approach.service.DraftApproachInfo
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.credentials.entity.Credentials

fun makeDraftApproachInfo(
    id: Long,
    name: String,
    aliases: List<String>,
    tags: List<String>,
    categories: List<Category>,
    owner: Credentials
): DraftApproachInfo = object : DraftApproachInfo {
    override val id = id
    override val aliases = aliases
    override val name = name
    override val tags = tags
    override val categories = categories
    override val owner = owner
}
