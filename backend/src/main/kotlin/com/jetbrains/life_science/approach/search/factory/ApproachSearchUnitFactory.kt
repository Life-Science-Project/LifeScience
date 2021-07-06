package com.jetbrains.life_science.approach.search.factory

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.approach.search.ApproachSearchUnit
import org.springframework.stereotype.Component

@Component
class ApproachSearchUnitFactory {

    fun create(approach: Approach): ApproachSearchUnit {
        return ApproachSearchUnit(approach.id, approach.name)
    }
}
