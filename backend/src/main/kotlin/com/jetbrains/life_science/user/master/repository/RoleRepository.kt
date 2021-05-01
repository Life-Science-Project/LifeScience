package com.jetbrains.life_science.user.master.repository

import com.jetbrains.life_science.user.master.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role
}
