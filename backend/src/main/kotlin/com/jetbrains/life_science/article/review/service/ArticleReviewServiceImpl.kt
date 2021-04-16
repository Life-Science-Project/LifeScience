package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.ArticleReview
import com.jetbrains.life_science.article.review.factory.ArticleReviewFactory
import com.jetbrains.life_science.article.review.repository.ArticleReviewRepository
import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.ArticleReviewNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleReviewServiceImpl(
    val repository: ArticleReviewRepository,
    val factory: ArticleReviewFactory,
    val articleVersionService: ArticleVersionService,
    val userService: UserService
) : ArticleReviewService {

    override fun addReview(info: ArticleReviewInfo): ArticleReview {
        val articleVersion = articleVersionService.getById(info.articleVersionId)
        val user = userService.getById(info.reviewerId)
        return repository.save(factory.create(info, articleVersion, user))
    }

    override fun deleteReview(reviewId: Long) {
        checkReviewExists(reviewId)
        repository.deleteById(reviewId)
    }

    override fun getAllByVersionId(articleVersionId: Long): List<ArticleReview> {
        return repository.findAllByArticleVersionId(articleVersionId)
    }

    override fun getById(reviewId: Long): ArticleReview {
        checkReviewExists(reviewId)
        return repository.getOne(reviewId)
    }

    @Transactional
    override fun updateById(reviewId: Long, reviewInfo: ArticleReviewInfo): ArticleReview {
        val review = getById(reviewId)
        val version = articleVersionService.getById(reviewInfo.articleVersionId)
        val user = userService.getById(reviewInfo.reviewerId)
        factory.setParams(review, reviewInfo, version, user)
        return review
    }

    private fun checkReviewExists(id: Long) {
        if (!repository.existsById(id)) {
            throw ArticleReviewNotFoundException("Review not found by id: $id")
        }
    }
}
