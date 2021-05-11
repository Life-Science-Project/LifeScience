package com.jetbrains.life_science.statistics.view

import com.jetbrains.life_science.statistics.entity.Statistics
import org.springframework.stereotype.Component

@Component
class StatisticsViewMapper {

    fun toView(statistics: Statistics): StatisticsView {
        return StatisticsView(
            usersCount = statistics.usersCount,
            articlesCount = statistics.articlesCount,
            organisationsCount = statistics.organisationsCount
        )
    }
}
