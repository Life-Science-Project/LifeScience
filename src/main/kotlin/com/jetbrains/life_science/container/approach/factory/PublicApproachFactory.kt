package com.jetbrains.life_science.container.approach.factory

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import org.springframework.stereotype.Component

@Component
class PublicApproachFactory {
    fun create(approach: DraftApproach): PublicApproach {
        return PublicApproach(
            id = 0,
            name = approach.name,
            aliases = approach.aliases.toMutableList(),
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
