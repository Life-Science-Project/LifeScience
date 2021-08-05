package com.jetbrains.life_science.container.approach.factory

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.service.DraftApproachInfo
import com.jetbrains.life_science.util.UTCZone
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DraftApproachFactory {
    fun create(info: DraftApproachInfo): DraftApproach {
        return DraftApproach(
            id = 0,
            name = info.name,
            aliases = info.aliases.toMutableList(),
            categories = info.categories.toMutableList(),
            tags = info.tags.toMutableList(),
            owner = info.owner,
            participants = mutableListOf(info.owner),
            sections = mutableListOf(),
            creationDate = LocalDateTime.now(UTCZone)
        )
    }

    fun setParams(draftApproach: DraftApproach, info: DraftApproachInfo) {
        draftApproach.name = info.name
        draftApproach.aliases = info.aliases.toMutableList()
        draftApproach.tags = info.tags.toMutableList()
        draftApproach.categories = info.categories.toMutableList()
    }
}
