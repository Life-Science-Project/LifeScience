package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.*

@Entity
class ApproachEditRecord(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "approach_edit_record_seq"
    )
    @SequenceGenerator(
        name = "approach_edit_record_seq",
        allocationSize = 1
    )
    override val id: Long,

    createdSections: MutableList<Section>,
    deletedSections: MutableList<Section>,

    @ManyToOne
    var approach: PublicApproach,

    @OneToOne(mappedBy = "editRecord")
    var request: ApproachReviewRequest?

) : EditRecord(deletedSections, createdSections)
