package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
abstract class ReviewRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    var date: Date,

    var state: RequestState,

    @OneToMany
    var reviews: MutableList<Review>,

    @ManyToOne
    var editor: Credentials
)
