package com.jetbrains.life_science.review.edit_record.repository

import com.jetbrains.life_science.review.edit_record.entity.ProtocolEditRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProtocolEditRecordRepository : JpaRepository<ProtocolEditRecord, Long>
