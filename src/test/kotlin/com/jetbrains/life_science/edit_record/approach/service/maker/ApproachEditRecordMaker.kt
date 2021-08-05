package com.jetbrains.life_science.edit_record.approach.service.maker

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordInfo
import java.time.LocalDateTime

fun makeApproachEditRecordInfo(
    id: Long,
    approach: PublicApproach,
    lastEditDate: LocalDateTime
): ApproachEditRecordInfo = object : ApproachEditRecordInfo {
    override val id = id
    override val lastEditDate = lastEditDate
    override val approach = approach
}
