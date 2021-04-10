package com.jetbrains.life_science.method.search.service

import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.search.MethodSearchUnit

interface MethodSearchUnitService {

    fun create(method: Method): MethodSearchUnit

    fun delete(id: Long)

    fun update(method: Method)

}
