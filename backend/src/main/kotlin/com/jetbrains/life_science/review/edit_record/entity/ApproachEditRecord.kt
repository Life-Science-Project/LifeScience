package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ApproachEditRecord(
    id: Long,
    createdSections: MutableList<Section>,
    deletedSections: MutableList<Section>,

    @ManyToOne
    var approach: PublicApproach

) : EditRecord(id, deletedSections, createdSections)
