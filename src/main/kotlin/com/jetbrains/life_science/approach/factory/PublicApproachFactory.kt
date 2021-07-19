package com.jetbrains.life_science.approach.factory

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.service.PublicApproachInfo
import org.springframework.stereotype.Component

@Component
class PublicApproachFactory {
    fun create(approach: DraftApproach): PublicApproach {
        return PublicApproach(
            id = 0,
            name = approach.name,
            sections = approach.sections,
            tags = approach.tags,
            owner = approach.owner,
            categories = approach.categories,
            creationDate = approach.creationDate,
            coAuthors = approach.participants,
            protocols = mutableListOf()
        )
    }
}
