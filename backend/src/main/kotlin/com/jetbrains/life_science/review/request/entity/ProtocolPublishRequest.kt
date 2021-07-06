package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.protocol.entity.Protocol
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.master.entity.User
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ProtocolPublishRequest(
    id: Long,
    date: Date,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: User,

    @ManyToOne
    var protocol: Protocol

) : ReviewRequest(id, date, state, reviews, editor)
