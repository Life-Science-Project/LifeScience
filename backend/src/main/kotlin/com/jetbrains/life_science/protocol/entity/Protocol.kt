package com.jetbrains.life_science.protocol.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@MappedSuperclass
abstract class Protocol(
    @Id
    val id: Long,

    var name: String,

    @ManyToOne
    var approach: PublicApproach,

    @OneToMany
    var sections: MutableList<Section>,

    @ManyToOne
    var owner: Credentials
)
