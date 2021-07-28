package com.jetbrains.life_science.review.request.service.editRecord

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

interface ProtocolReviewRequestInfo {
    val id: Long

    val date: LocalDateTime

    val editor: Credentials

    val protocolEditRecord: ProtocolEditRecord
}
