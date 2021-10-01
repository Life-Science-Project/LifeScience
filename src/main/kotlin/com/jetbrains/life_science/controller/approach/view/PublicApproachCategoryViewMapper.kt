package com.jetbrains.life_science.controller.approach.view

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import org.springframework.stereotype.Component

@Component
class PublicApproachCategoryViewMapper {
    fun createView(approach: PublicApproach): PublicApproachInCategoryView {
        return PublicApproachInCategoryView(
            id = approach.id,
            name = approach.name
        )
    }
}
