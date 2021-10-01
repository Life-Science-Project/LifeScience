package com.jetbrains.life_science.edit_record.repository

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import org.springframework.data.jpa.repository.JpaRepository

interface ProtocolEditRecordRepository : JpaRepository<ProtocolEditRecord, Long>
