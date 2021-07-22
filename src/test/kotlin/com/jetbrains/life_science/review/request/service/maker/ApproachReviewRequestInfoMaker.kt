package com.jetbrains.life_science.review.request.service.maker

import com.jetbrains.life_science.review.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.review.request.service.editRecord.ApproachReviewRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

fun makeApproachReviewRequestInfo(
    id: Long,
    date: LocalDateTime,
    editor: Credentials,
    editRecord: ApproachEditRecord
): ApproachReviewRequestInfo = object : ApproachReviewRequestInfo {
    override val id = id
    override val date = date
    override val editor = editor
    override val approachEditRecord = editRecord
}
