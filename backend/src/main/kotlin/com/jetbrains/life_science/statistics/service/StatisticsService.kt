package com.jetbrains.life_science.statistics.service

import com.jetbrains.life_science.statistics.entity.Statistics

interface StatisticsService {

    fun getHomePageStatistics(): Statistics
}
