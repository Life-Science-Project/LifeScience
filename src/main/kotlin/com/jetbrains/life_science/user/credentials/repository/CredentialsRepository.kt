package com.jetbrains.life_science.user.credentials.repository

import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CredentialsRepository : JpaRepository<Credentials, Long> {
    fun findByEmail(email: String): Optional<Credentials>
    fun existsByEmail(email: String): Boolean
}
