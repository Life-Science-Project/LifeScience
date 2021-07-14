package com.jetbrains.life_science.approach.factory

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.service.PublicApproachInfo
import org.springframework.stereotype.Component

@Component
class PublicApproachFactory {
    fun create(info: PublicApproachInfo): PublicApproach {
        return PublicApproach(
            id = 0,
            name = info.approach.name,
            sections = info.approach.sections,
            tags = info.approach.tags,
            owner = info.approach.owner,
            categories = info.approach.categories,
            creationDate = info.approach.creationDate,
            coAuthors = info.approach.participants,
            protocols = mutableListOf()
        )
    }
}
