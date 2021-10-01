package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import java.time.LocalDateTime

interface ApproachEditRecordInfo {

    val id: Long

    val lastEditDate: LocalDateTime

    val approach: PublicApproach
}
