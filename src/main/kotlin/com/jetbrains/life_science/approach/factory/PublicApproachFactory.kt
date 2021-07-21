package com.jetbrains.life_science.approach.factory

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import org.springframework.stereotype.Component

@Component
class PublicApproachFactory {
    fun create(approach: DraftApproach): PublicApproach {
        return PublicApproach(
            id = 0,
            name = approach.name,
            sections = approach.sections.toMutableList(),
            tags = approach.tags.toMutableList(),
            owner = approach.owner,
            categories = approach.categories.toMutableList(),
            creationDate = approach.creationDate,
            coAuthors = approach.participants.toMutableList(),
            protocols = mutableListOf()
        )
    }
}
