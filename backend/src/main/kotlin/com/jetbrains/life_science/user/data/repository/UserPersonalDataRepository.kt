package com.jetbrains.life_science.user.data.repository

import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserPersonalDataRepository : JpaRepository<UserPersonalData, Long>
