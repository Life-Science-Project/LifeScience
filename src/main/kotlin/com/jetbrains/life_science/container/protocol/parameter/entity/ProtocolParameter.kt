package com.jetbrains.life_science.container.protocol.parameter.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator

@Entity
class ProtocolParameter(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "parameter_seq"
    )
    @SequenceGenerator(
        name = "parameter_seq",
        allocationSize = 1
    )
    val id: Long,

    var name: String,

    var value: String
)
