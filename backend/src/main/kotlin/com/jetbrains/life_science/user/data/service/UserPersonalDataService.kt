package com.jetbrains.life_science.user.data.service

import com.jetbrains.life_science.user.credentials.service.NewUserInfo
import com.jetbrains.life_science.user.data.entity.UserPersonalData

interface UserPersonalDataService {

    fun getAllUsers(): List<UserPersonalData>

    fun getById(id: Long): UserPersonalData

    fun update(info: UserPersonalDataInfo, userPersonalData: UserPersonalData): UserPersonalData

    fun deleteById(id: Long)

    fun countAll(): Long
}
