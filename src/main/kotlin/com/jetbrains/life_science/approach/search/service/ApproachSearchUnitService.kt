package com.jetbrains.life_science.approach.search.service

import com.jetbrains.life_science.approach.entity.PublicApproach

interface ApproachSearchUnitService {

    fun createSearchUnit(approach: PublicApproach)

    fun deleteSearchUnitById(id: Long)

    fun update(approach: PublicApproach)

    fun getContext(approachId: Long): List<String>
}
