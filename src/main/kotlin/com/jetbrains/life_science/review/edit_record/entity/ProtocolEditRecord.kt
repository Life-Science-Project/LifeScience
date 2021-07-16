package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.*

@Entity
class ProtocolEditRecord(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "protocol_edit_record_seq"
    )
    @SequenceGenerator(
        name = "protocol_edit_record_seq",
        allocationSize = 1
    )
    override val id: Long,

    createdSections: MutableList<Section>,
    deletedSections: MutableList<Section>,

    @ManyToOne
    var protocol: PublicProtocol,

    @OneToOne(mappedBy = "editRecord")
    var request: ProtocolReviewRequest?

) : EditRecord(deletedSections, createdSections)
