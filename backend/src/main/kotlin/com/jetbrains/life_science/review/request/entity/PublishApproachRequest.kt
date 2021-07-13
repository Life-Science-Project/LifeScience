package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class PublishApproachRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: Credentials,

    @ManyToOne
    var approach: DraftApproach

) : ReviewRequest(id, date, state, reviews, editor)
