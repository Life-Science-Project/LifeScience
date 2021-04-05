package com.jetbrains.life_science.user.repository

import com.jetbrains.life_science.user.entity.User
import java.util.Optional
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional

interface UserRepository : JpaRepository<User, Long> {

    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    @Transactional
    fun deleteByUsername(username: String)
}