package com.jetbrains.life_science.user.master.repository

import com.jetbrains.life_science.user.master.entity.UserCredentials
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserCredentialsRepository : JpaRepository<UserCredentials, Long> {
    fun findByEmail(username: String): Optional<UserCredentials>
}
