package com.jetbrains.life_science.edit_record.entity

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import java.time.LocalDateTime
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

    lastEditDate: LocalDateTime,
    deletedSections: MutableList<Section>,
    createdSections: MutableList<Section>,

    @OneToOne
    val approach: PublicApproach

) : EditRecord(lastEditDate, deletedSections, createdSections) {
    fun containsSectionById(id: Long): Boolean {
        return approach.sections.any { it.id == id }
    }

    fun containsDeletedSectionById(id: Long): Boolean {
        return deletedSections.any { it.id == id }
    }

    fun containsCreatedSectionById(id: Long): Boolean {
        return createdSections.any { it.id == id }
    }
}
