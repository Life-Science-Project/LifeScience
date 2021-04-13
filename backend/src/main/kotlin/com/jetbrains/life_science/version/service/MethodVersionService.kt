package com.jetbrains.life_science.version.service

import com.jetbrains.life_science.version.entity.MethodVersion

interface MethodVersionService {

    fun createBlank(info: MethodVersionInfo): MethodVersion

    fun getVersionById(id: Long): MethodVersion

    fun approve(id: Long)

    fun createCopy(methodId: Long)

    fun getById(id: Long): MethodVersion
}
