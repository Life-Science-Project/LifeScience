package com.jetbrains.life_science.review.response.entity

import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "review_seq"
    )
    @SequenceGenerator(name = "review_seq")
    val id: Long,

    var date: LocalDateTime,

    var comment: String,

    var resolution: ReviewResolution,

    @ManyToOne
    var reviewer: Credentials
)
