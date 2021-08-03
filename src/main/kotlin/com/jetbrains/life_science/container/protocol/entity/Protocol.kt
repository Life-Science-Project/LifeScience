package com.jetbrains.life_science.container.protocol.entity

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@MappedSuperclass
abstract class Protocol(

    var name: String,

    @ManyToOne
    var approach: PublicApproach,

    @OneToMany
    var sections: MutableList<Section>,

    @ManyToOne
    var owner: Credentials,

    @OneToMany
    var parameters: MutableList<ProtocolParameter>
) {
    abstract val id: Long
}
