package com.jetbrains.life_science.review.service

import com.jetbrains.life_science.review.entity.MethodReview
import com.jetbrains.life_science.review.factory.MethodReviewFactory
import com.jetbrains.life_science.review.repository.MethodReviewRepository
import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.version.service.MethodVersionService
import org.springframework.stereotype.Service

@Service
class MethodReviewServiceImpl(
    val repository: MethodReviewRepository,
    val factory: MethodReviewFactory,
    val methodVersionService: MethodVersionService,
    val userService: UserService
) : MethodReviewService {

    override fun addReview(info: MethodReviewInfo): MethodReview {
        val methodVersion = methodVersionService.getById(info.methodId)
        val user = userService.getUserById(info.authorId)
        return repository.save(factory.create(info, methodVersion, user))
    }
}
