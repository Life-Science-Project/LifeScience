package com.jetbrains.life_science.section.entity

import com.jetbrains.life_science.protocol.entity.Protocol
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ProtocolSection(
    id: Long,
    name: String,
    order: Long,
    visible: Boolean,

    @ManyToOne
    var protocol: Protocol

) : Section(id, name, order, visible)
