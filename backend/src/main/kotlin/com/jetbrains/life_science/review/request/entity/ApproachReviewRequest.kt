package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.review.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import java.util.Date
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class ApproachReviewRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: UserPersonalData,

    @OneToOne
    var editRecord: ApproachEditRecord

) : ReviewRequest(id, date, state, reviews, editor)
