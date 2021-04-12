package com.jetbrains.life_science.method.search.service

import com.jetbrains.life_science.method.entity.Method

interface MethodSearchUnitService {

    fun create(method: Method)

    fun delete(id: Long)

    fun update(method: Method)
}
