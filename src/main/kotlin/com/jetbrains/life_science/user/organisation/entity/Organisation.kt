package com.jetbrains.life_science.user.organisation.entity

import javax.persistence.*

@Entity
class Organisation(

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "organization_seq"
    )
    @SequenceGenerator(
        name = "organization_seq",
        allocationSize = 1
    )
    var id: Long,

    val name: String

)
