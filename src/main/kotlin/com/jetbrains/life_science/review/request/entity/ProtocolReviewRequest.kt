package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ProtocolReviewRequest(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "protocol_review_request_seq"
    )
    @SequenceGenerator(
        name = "protocol_review_request_seq",
        allocationSize = 1
    )
    override val id: Long,

    date: LocalDateTime,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: Credentials,

    @OneToOne
    var editRecord: ProtocolEditRecord

) : ReviewRequest(date, state, reviews, editor)
