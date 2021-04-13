package com.jetbrains.life_science.version.search

import com.jetbrains.life_science.version.entity.MethodVersion

interface MethodVersionSearchUnitService {

    fun createSearchUnit(version: MethodVersion)

}
