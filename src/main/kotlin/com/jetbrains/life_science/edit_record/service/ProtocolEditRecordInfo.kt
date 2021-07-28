package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import java.time.LocalDateTime

interface ProtocolEditRecordInfo {

    val id: Long

    val lastEditDate: LocalDateTime

    val protocol: PublicProtocol
}
