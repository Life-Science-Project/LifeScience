package com.jetbrains.life_science.container.approach.search.factory

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.search.ApproachSearchUnit
import org.springframework.stereotype.Component

@Component
class ApproachSearchUnitFactory {

    fun create(approach: PublicApproach, context: List<String>): ApproachSearchUnit {
        val aliases = mutableListOf(approach.name)
        aliases.addAll(approach.aliases)
        return ApproachSearchUnit(approach.id, aliases, context)
    }
}
