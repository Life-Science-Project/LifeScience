package com.jetbrains.life_science.approach.draft.service

import com.jetbrains.life_science.approach.draft.entity.DraftApproach

interface DraftApproachService {

    fun getApproach(id: Long): DraftApproach

}