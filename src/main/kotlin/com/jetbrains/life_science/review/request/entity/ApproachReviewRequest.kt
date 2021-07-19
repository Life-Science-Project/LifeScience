package com.jetbrains.life_science.review.request.entity

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ApproachReviewRequest(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "approach_review_request_seq"
    )
    @SequenceGenerator(
        name = "approach_review_request_seq",
        allocationSize = 1
    )
    override val id: Long,

    date: LocalDateTime,
    state: RequestState,
    reviews: MutableList<Review>,
    editor: Credentials,

    @OneToOne
    var editRecord: ApproachEditRecord

) : ReviewRequest(date, state, reviews, editor)
