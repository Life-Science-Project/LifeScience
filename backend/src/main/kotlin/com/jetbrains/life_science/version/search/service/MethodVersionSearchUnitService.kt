package com.jetbrains.life_science.version.search.service

import com.jetbrains.life_science.version.entity.MethodVersion

interface MethodVersionSearchUnitService {

    fun createSearchUnit(version: MethodVersion)

    fun deleteSearchUnitById(id: Long)

}
