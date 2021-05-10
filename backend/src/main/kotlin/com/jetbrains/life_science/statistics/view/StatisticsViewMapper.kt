package com.jetbrains.life_science.statistics.view

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RestController

@Component
class StatisticsViewMapper {

    fun toView(usersCount: Long, articlesCount: Long): StatisticsView {
        return StatisticsView(usersCount, articlesCount)
    }

}
