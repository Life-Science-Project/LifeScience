package com.jetbrains.life_science.user.user.repository

import com.jetbrains.life_science.user.user.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role
}
