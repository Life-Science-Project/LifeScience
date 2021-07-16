package com.jetbrains.life_science.approach.search.factory

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.approach.search.ApproachSearchUnit
import org.springframework.stereotype.Component

@Component
class ApproachSearchUnitFactory {

    fun create(approach: Approach, context: List<String>): ApproachSearchUnit {
        return ApproachSearchUnit(approach.id, listOf(approach.name), context)
    }
}
