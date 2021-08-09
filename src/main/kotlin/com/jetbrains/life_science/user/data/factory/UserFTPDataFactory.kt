package com.jetbrains.life_science.user.data.factory

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserFTPData
import org.springframework.stereotype.Component

@Component
class UserFTPDataFactory {
    fun create(credentials: Credentials): UserFTPData {
        return UserFTPData(
            id = 0,
            credentials = credentials,
            fileNames = mutableListOf()
        )
    }
}
