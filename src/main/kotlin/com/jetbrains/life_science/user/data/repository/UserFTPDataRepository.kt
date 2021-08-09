package com.jetbrains.life_science.user.data.repository

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserFTPData
import org.springframework.data.jpa.repository.JpaRepository

interface UserFTPDataRepository : JpaRepository<UserFTPData, Long> {

    fun findByCredentials(credentials: Credentials): UserFTPData?

    fun existsByFileNamesContains(filename: String): Boolean
}
