package com.jetbrains.life_science.version.service

import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.entity.State

interface MethodVersionService {

    fun createBlack(info: MethodVersionInfo): MethodVersion

    fun getVersionById(id: Long): MethodVersion

    fun approve(id: Long)

    fun createCopy(methodId: Long)

    fun getPublishedVersion(methodId: Long): MethodVersion
}
