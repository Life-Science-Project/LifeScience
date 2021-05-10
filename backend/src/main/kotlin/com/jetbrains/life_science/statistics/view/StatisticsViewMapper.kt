package com.jetbrains.life_science.statistics.view

import org.springframework.stereotype.Component

@Component
class StatisticsViewMapper {

    fun toView(usersCount: Long, articlesCount: Long): StatisticsView {
        return StatisticsView(usersCount, articlesCount)
    }
}
