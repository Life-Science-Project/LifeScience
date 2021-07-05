package com.jetbrains.life_science.section.entity

import com.jetbrains.life_science.protocol.entity.Protocol
import javax.persistence.ManyToOne

class ProtocolSection(
    id: Long,
    name: String,
    order: Long,

    @ManyToOne
    var protocol: Protocol

) : Section(id, name, order)
