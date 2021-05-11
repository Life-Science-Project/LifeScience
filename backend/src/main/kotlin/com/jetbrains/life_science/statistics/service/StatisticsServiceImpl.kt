package com.jetbrains.life_science.statistics.service

import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.statistics.entity.Statistics
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.user.organisation.service.OrganisationService
import org.springframework.stereotype.Service

@Service
class StatisticsServiceImpl(
    val userService: UserService,
    val articleService: ArticleService,
    val organisationService: OrganisationService
) : StatisticsService {

    override fun getHomePageStatistics(): Statistics {
        val usersCount = userService.countAll()
        val articlesCount = articleService.countAll()
        val organisationsCount = organisationService.countAll()
        return Statistics(usersCount, articlesCount, organisationsCount)
    }
}
