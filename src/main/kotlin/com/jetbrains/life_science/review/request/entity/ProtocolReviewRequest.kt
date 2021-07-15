package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.review.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ProtocolReviewRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long,

    date: LocalDateTime,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: Credentials,

    @OneToOne
    var editRecord: ProtocolEditRecord

) : ReviewRequest(date, state, reviews, editor)
