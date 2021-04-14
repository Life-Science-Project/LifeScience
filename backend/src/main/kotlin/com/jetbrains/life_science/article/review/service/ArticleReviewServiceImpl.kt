package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.ArticleReview
import com.jetbrains.life_science.article.review.factory.ArticleReviewFactory
import com.jetbrains.life_science.article.review.repository.ArticleReviewRepository
import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import org.springframework.stereotype.Service

@Service
class ArticleReviewServiceImpl(
    val repository: ArticleReviewRepository,
    val factory: ArticleReviewFactory,
    val articleVersionService: ArticleVersionService,
    val userService: UserService
) : ArticleReviewService {

    override fun addReview(info: ArticleReviewInfo): ArticleReview {
        val articleVersion = articleVersionService.getById(info.articleVersionId)
        val user = userService.getById(info.authorId)
        return repository.save(factory.create(info, articleVersion, user))
    }
}
