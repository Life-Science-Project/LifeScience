package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.method.entity.Method

interface MethodService {

    fun create(methodInfo: MethodInfo): Method

    fun deleteByID(id: Long)

    fun getMethod(id: Long): Method
}
