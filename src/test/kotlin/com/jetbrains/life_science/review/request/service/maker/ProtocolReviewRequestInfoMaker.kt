package com.jetbrains.life_science.review.request.service.maker

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.review.request.service.editRecord.ProtocolReviewRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

fun makeProtocolReviewRequestInfo(
    id: Long,
    date: LocalDateTime,
    editor: Credentials,
    editRecord: ProtocolEditRecord
): ProtocolReviewRequestInfo = object : ProtocolReviewRequestInfo {
    override val id = id
    override val date = date
    override val editor = editor
    override val protocolEditRecord = editRecord
}
