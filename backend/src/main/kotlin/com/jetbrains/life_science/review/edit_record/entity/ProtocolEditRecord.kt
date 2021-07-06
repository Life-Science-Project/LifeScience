package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ProtocolEditRecord(
    id: Long,
    createdSections: MutableList<Section>,
    deletedSections: MutableList<Section>,

    @ManyToOne
    var protocol: PublicProtocol

) : EditRecord(id, deletedSections, createdSections)
