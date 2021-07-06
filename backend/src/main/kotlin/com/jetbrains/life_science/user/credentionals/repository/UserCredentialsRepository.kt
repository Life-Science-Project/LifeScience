package com.jetbrains.life_science.user.credentionals.repository

import com.jetbrains.life_science.user.credentionals.entity.UserCredentials
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserCredentialsRepository : JpaRepository<UserCredentials, Long> {
    fun findByEmail(email: String): Optional<UserCredentials>
}
