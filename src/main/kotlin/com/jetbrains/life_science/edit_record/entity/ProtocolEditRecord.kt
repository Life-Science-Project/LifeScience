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

) : EditRecord(lastEditDate, deletedSections, createdSections)
