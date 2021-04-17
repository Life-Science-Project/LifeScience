package com.jetbrains.life_science.user.credentials.repository

import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserCredentialsRepository : JpaRepository<UserCredentials, Long> {

    fun findByEmail(email: String): Optional<UserCredentials>

    fun existsByEmail(email: String): Boolean
}
