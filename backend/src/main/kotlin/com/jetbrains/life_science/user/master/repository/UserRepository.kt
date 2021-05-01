package com.jetbrains.life_science.user.master.repository

import com.jetbrains.life_science.user.master.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun getByEmail(email: String): User
    fun existsByEmail(email: String): Boolean
}
