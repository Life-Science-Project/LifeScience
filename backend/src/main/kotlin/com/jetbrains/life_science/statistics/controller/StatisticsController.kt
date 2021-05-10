package com.jetbrains.life_science.statistics.controller

import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.statistics.view.StatisticsView
import com.jetbrains.life_science.statistics.view.StatisticsViewMapper
import com.jetbrains.life_science.user.master.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/statistics")
class StatisticsController(
    val userService: UserService,
    val articleService: ArticleService,
    val viewMapper: StatisticsViewMapper
) {

    @GetMapping("/api/fix-versions")
    fun getStatistics(): StatisticsView {
        val usersCount = userService.countAll()
        val articlesCount = articleService.countAll()
        return viewMapper.toView(usersCount, articlesCount)
    }

}
