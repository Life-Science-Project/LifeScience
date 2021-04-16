package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.ArticleReview
import com.jetbrains.life_science.article.review.factory.ArticleReviewFactory
import com.jetbrains.life_science.article.review.repository.ArticleReviewRepository
import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.ArticleReviewNotFoundException
import com.jetbrains.life_science.user.entity.User
import org.springframework.security.access.AccessDeniedException
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

    override fun getAllByVersionId(articleVersionId: Long, user: User): List<ArticleReview> {
        return repository.findAllByArticleVersionId(articleVersionId).filter {
            checkAccess(it.reviewer, it.articleVersion.author, user)
        }
    }

    override fun getById(reviewId: Long, user: User): ArticleReview {
        checkReviewExists(reviewId)
        val review = repository.getOne(reviewId)
        if (checkAccess(review.reviewer, review.articleVersion.author, user)) {
            return review
        } else {
            throw AccessDeniedException("You haven't got enough permissions to see this review")
        }
    }

    @Transactional
    override fun updateById(reviewId: Long, reviewInfo: ArticleReviewInfo, user: User): ArticleReview {
        val review = getById(reviewId, user)
        val version = articleVersionService.getById(reviewInfo.articleVersionId)
        val reviewer = userService.getById(reviewInfo.reviewerId)
        factory.setParams(review, reviewInfo, version, reviewer)
        return review
    }

    private fun checkReviewExists(id: Long) {
        if (!repository.existsById(id)) {
            throw ArticleReviewNotFoundException("Review not found by id: $id")
        }
    }

    private fun checkAccess(reviewer: User, author: User, user: User): Boolean {
        return reviewer.id == user.id
            || author.id == user.id
            || user.roles.any {
            it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR"
        }
    }
}
