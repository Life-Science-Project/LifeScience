package com.jetbrains.life_science.user.data.service

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserFTPData

interface UserFTPDataService {

    fun getByCredentials(credentials: Credentials): UserFTPData

    fun contains(fileName: String, credentials: Credentials): Boolean

    fun addFileName(credentials: Credentials, fileName: String): UserFTPData

    fun removeFileName(credentials: Credentials, fileName: String): UserFTPData
}
