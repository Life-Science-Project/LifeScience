package com.jetbrains.life_science.statistics.controller

import com.jetbrains.life_science.statistics.service.StatisticsService
import com.jetbrains.life_science.statistics.view.StatisticsView
import com.jetbrains.life_science.statistics.view.StatisticsViewMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/statistics")
class StatisticsController(
    val viewMapper: StatisticsViewMapper,
    val service: StatisticsService
) {

    @GetMapping("/api/fix-versions")
    fun getStatistics(): StatisticsView {
        val statistics = service.getHomePageStatistics()
        return viewMapper.toView(statistics)
    }
}
