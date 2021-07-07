package com.jetbrains.life_science.user.credentials.repository

import com.jetbrains.life_science.user.credentials.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role
}
