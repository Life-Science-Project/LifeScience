package com.jetbrains.life_science.category.service.maker

import com.jetbrains.life_science.category.service.CategoryUpdateInfo

fun makeCategoryUpdateInfo(
    id: Long,
    name: String,
    aliases: List<String>,
    parentsToAddIds: List<Long>,
    parentsToDeleteIds: List<Long>
) = object : CategoryUpdateInfo {
    override val id = id
    override val name = name
    override val aliases = aliases
    override val parentsToAddIds = parentsToAddIds
    override val parentsToDeleteIds = parentsToDeleteIds
}
