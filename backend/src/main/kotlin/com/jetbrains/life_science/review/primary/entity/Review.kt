package com.jetbrains.life_science.review.primary.entity

import com.jetbrains.life_science.review.request.entity.ReviewRequest
import com.jetbrains.life_science.user.master.entity.User
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var date: Date,

    var comment: String,

    var resolution: ReviewResolution,

    @ManyToOne
    var reviewer: User,

    @ManyToOne
    var request: ReviewRequest
)
