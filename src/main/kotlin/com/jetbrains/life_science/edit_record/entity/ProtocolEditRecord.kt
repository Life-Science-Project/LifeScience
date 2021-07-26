package com.jetbrains.life_science.edit_record.entity

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section
import java.time.LocalDateTime
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

    lastEditDate: LocalDateTime,
    deletedSections: MutableList<Section>,
    createdSections: MutableList<Section>,

    @OneToOne
    val protocol: PublicProtocol

) : EditRecord(lastEditDate, deletedSections, createdSections) {
    fun containsSectionById(id: Long): Boolean {
        return protocol.sections.any { it.id == id }
    }

    fun containsDeletedSectionById(id: Long): Boolean {
        return deletedSections.any { it.id == id }
    }

    fun containsCreatedSectionById(id: Long): Boolean {
        return createdSections.any { it.id == id }
    }
}
