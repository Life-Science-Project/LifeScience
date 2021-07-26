package com.jetbrains.life_science.review.request.service.editRecord

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

interface ApproachReviewRequestInfo {
    val date: LocalDateTime
    val editor: Credentials
    val approachEditRecord: ApproachEditRecord
}
