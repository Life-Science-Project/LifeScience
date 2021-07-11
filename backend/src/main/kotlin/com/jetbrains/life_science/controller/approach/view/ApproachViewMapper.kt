package com.jetbrains.life_science.controller.approach.view

import com.jetbrains.life_science.approach.entity.Approach
import org.springframework.stereotype.Component

@Component
class ApproachViewMapper {

    fun toViewShort(approach: Approach): ApproachShortView {
        return ApproachShortView(
            id = approach.id,
            name = approach.name,
            creationDate = approach.creationDate,
            tags = approach.tags
        )
    }

}