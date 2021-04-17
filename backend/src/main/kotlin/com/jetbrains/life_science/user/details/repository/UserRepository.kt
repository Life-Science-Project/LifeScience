package com.jetbrains.life_science.user.details.repository

import com.jetbrains.life_science.user.details.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
