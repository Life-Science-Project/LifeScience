package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ProtocolPublishRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: UserPersonalData,

    @ManyToOne
    var protocol: DraftProtocol

) : ReviewRequest(id, date, state, reviews, editor)
