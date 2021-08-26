package com.jetbrains.life_science.category.service.maker

import com.jetbrains.life_science.category.service.CategoryInfo

fun makeCategoryInfo(
    name: String,
    parentId: Long?,
    aliases: List<String>
) = object : CategoryInfo {
    override val name = name
    override val parentId = parentId
    override val aliases = aliases
}
