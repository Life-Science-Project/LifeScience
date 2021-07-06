package com.jetbrains.life_science.review.edit_record.repository

import com.jetbrains.life_science.review.edit_record.entity.EditRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EditRecordRepository : JpaRepository<EditRecord, Long>
