package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class PublishProtocolRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override val id: Long,

    date: LocalDateTime,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: Credentials,

    @ManyToOne
    var protocol: DraftProtocol

) : ReviewRequest(date, state, reviews, editor)
