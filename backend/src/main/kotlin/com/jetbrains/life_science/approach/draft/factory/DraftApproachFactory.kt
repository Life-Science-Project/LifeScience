package com.jetbrains.life_science.approach.draft.factory

import com.jetbrains.life_science.approach.draft.entity.DraftApproach
import com.jetbrains.life_science.approach.draft.service.DraftApproachCreationInfo
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DraftApproachFactory {

    fun create(draftApproachInfo: DraftApproachCreationInfo): DraftApproach {
        return DraftApproach(
            id = 0,
            name = draftApproachInfo.name,
            categories = mutableListOf(draftApproachInfo.parentCategory),
            creationDate = LocalDateTime.now(),
            owner = draftApproachInfo.creator,
            participants = mutableListOf(draftApproachInfo.creator)
        )
    }
}
