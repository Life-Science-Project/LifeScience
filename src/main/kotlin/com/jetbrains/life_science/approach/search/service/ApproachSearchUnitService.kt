package com.jetbrains.life_science.approach.search.service

import com.jetbrains.life_science.approach.entity.Approach

interface ApproachSearchUnitService {

    fun createSearchUnit(approach: Approach)

    fun deleteSearchUnitById(id: Long)

    fun update(approach: Approach)
}
