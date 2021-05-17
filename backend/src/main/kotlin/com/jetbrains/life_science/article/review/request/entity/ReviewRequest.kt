package com.jetbrains.life_science.article.review.request.entity

import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import javax.persistence.*

@Entity
class ReviewRequest(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    val version: ArticleVersion,

    @Enumerated
    val destination: VersionDestination,

    @OneToOne(mappedBy = "reviewRequest")
    var resolution: Review?
)
