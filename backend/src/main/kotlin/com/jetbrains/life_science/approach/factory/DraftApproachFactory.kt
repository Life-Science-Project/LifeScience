package com.jetbrains.life_science.approach.factory

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.service.DraftApproachInfo
import org.springframework.stereotype.Component

@Component
class DraftApproachFactory {
    fun create(info: DraftApproachInfo): DraftApproach {
        return DraftApproach(
            id = 0,
            name = info.name,
            categories = info.categories.toMutableList(),
            tags = info.tags,
            owner = info.owner,
            participants = mutableListOf(info.owner),
            sections = mutableListOf()
        )
    }

    fun setParams(draftApproach: DraftApproach, info: DraftApproachInfo) {
        draftApproach.name = info.name
        draftApproach.tags = info.tags
        draftApproach.categories = info.categories.toMutableList()
    }
}
