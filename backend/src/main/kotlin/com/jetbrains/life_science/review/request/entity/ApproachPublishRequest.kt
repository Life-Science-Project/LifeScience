package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.master.entity.User
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ApproachPublishRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: User,

    @ManyToOne
    var approach: Approach

) : ReviewRequest(id, date, state, reviews, editor)
