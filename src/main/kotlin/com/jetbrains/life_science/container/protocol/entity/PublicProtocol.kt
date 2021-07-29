package com.jetbrains.life_science.container.protocol.entity

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@Entity
class PublicProtocol(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "public_protocol_seq"
    )
    @SequenceGenerator(
        name = "public_protocol_seq",
        allocationSize = 1
    )
    override val id: Long,

    name: String,
    approach: PublicApproach,
    sections: MutableList<Section>,
    owner: Credentials,

    var rating: Long,

    @ManyToMany
    var coAuthors: MutableList<Credentials>

) : Protocol(name, approach, sections, owner)
