package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class ProtocolEditRecord(
    id: Long,
    createdSections: MutableList<Section>,
    deletedSections: MutableList<Section>,

    @ManyToOne
    var protocol: PublicProtocol,

    @OneToOne(mappedBy = "editRecord")
    var request: ProtocolReviewRequest

) : EditRecord(id, deletedSections, createdSections)
