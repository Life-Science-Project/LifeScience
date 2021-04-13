package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.method.entity.Method

interface MethodService {

    fun create(info: MethodInfo)

    fun getById(id: Long): Method
}
