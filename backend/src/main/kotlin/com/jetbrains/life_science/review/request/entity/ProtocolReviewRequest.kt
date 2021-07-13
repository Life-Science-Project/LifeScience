package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.review.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import java.util.Date
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class ProtocolReviewRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: Credentials,

    @OneToOne
    var editRecord: ProtocolEditRecord

) : ReviewRequest(id, date, state, reviews, editor)
