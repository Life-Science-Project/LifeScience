package com.jetbrains.life_science.article.review.response.entity

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User
import javax.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    var reviewRequest: ReviewRequest,

    @Column(nullable = false)
    var comment: String,

    @ManyToOne
    var reviewer: User,

    @Enumerated
    var resolution: ReviewResolution,

) {
    val version: ArticleVersion get() = reviewRequest.version
}
