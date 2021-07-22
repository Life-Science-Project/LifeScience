package com.jetbrains.life_science.edit_record.protocol.service.maker

import com.jetbrains.life_science.edit_record.service.ProtocolEditRecordInfo
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import java.time.LocalDateTime

fun makeProtocolEditRecordInfo(
    id: Long,
    protocol: PublicProtocol,
    lastEditDate: LocalDateTime
): ProtocolEditRecordInfo = object : ProtocolEditRecordInfo {
    override val id = id
    override val lastEditDate = lastEditDate
    override val protocol = protocol
}
