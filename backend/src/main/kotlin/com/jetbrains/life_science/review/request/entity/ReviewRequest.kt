package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class ReviewRequest(

    var date: LocalDateTime,

    var state: RequestState,

    @OneToMany
    var reviews: MutableList<Review>,

    @ManyToOne
    var editor: Credentials
) {
    abstract val id: Long
}
