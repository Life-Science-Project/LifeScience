package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.entity.MethodInfo

interface MethodService {

    fun create(methodInfo: MethodInfo): Method

    fun deleteMethod(id: Long)

    fun getMethod(id: Long): Method
}
