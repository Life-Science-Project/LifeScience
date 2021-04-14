package com.jetbrains.life_science.category.dto

import com.jetbrains.life_science.category.entity.CategoryInfo

class CategoryDTOToInfoAdapter(private val categoryDTO: CategoryDTO) : CategoryInfo {

    override fun getID(): Long {
        return 0
    }

    override fun getName(): String {
        return categoryDTO.name
    }

    override fun getParentID(): Long? {
        return categoryDTO.parentID
    }
}
