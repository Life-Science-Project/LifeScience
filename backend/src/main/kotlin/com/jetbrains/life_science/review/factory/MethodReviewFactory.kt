package com.jetbrains.life_science.review.factory

import com.jetbrains.life_science.review.entity.MethodReview
import com.jetbrains.life_science.review.service.MethodReviewInfo
import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.version.entity.MethodVersion
import org.springframework.stereotype.Component

@Component
class MethodReviewFactory {
    fun create(info: MethodReviewInfo, methodVersion: MethodVersion, user: User): MethodReview {
        return MethodReview(0, methodVersion, info.comment, user)
    }
}
