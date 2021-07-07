package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ApproachPublishRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: UserPersonalData,

    @ManyToOne
    var approach: DraftApproach

) : ReviewRequest(id, date, state, reviews, editor)
