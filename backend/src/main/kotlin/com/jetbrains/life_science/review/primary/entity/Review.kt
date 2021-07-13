package com.jetbrains.life_science.review.primary.entity

import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
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

    var date: LocalDateTime,

    var comment: String,

    var resolution: ReviewResolution,

    @ManyToOne
    var reviewer: Credentials,
)
