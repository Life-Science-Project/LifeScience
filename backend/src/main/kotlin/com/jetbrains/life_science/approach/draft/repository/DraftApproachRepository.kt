package com.jetbrains.life_science.approach.draft.repository

import com.jetbrains.life_science.approach.draft.entity.DraftApproach
import org.springframework.data.jpa.repository.JpaRepository

interface DraftApproachRepository : JpaRepository<DraftApproach, Long> {

    fun findDraftApproachById(id: Long): DraftApproach?

}
