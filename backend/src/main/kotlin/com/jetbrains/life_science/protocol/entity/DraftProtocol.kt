package com.jetbrains.life_science.protocol.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class DraftProtocol(
    id: Long,
    name: String,
    approach: PublicApproach,
    sections: MutableList<Section>,
    owner: Credentials,

    @ManyToMany
    var participants: MutableList<Credentials>

) : Protocol(id, name, approach, sections, owner)
