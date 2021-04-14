package com.jetbrains.life_science.category.dto

import com.jetbrains.life_science.category.service.CategoryInfo

class CategoryDTOToInfoAdapter(private val categoryDTO: CategoryDTO) : CategoryInfo {

    override fun getId(): Long {
        return 0
    }

    override fun getName(): String {
        return categoryDTO.name
    }

    override fun getParentId(): Long? {
        return categoryDTO.parentId
    }
}
