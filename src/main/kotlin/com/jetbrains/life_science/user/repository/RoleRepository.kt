package com.jetbrains.life_science.user.repository

import com.jetbrains.life_science.user.entity.Role
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role
}
