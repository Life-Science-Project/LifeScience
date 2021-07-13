package com.jetbrains.life_science.approach.published.service.maker

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.service.PublicApproachInfo

fun makePublicApproachInfo(
    draftApproach: DraftApproach
) = object : PublicApproachInfo {
    override val approach = draftApproach
}
