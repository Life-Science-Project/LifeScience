package com.jetbrains.life_science.container.protocol.entity

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@Entity
class DraftProtocol(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "draft_protocol_seq"
    )
    @SequenceGenerator(
        name = "draft_protocol_seq",
        allocationSize = 1
    )
    override val id: Long,

    name: String,
    approach: PublicApproach,
    sections: MutableList<Section>,
    owner: Credentials,

    @ManyToMany
    var participants: MutableList<Credentials>

) : Protocol(name, approach, sections, owner)
