package com.jetbrains.life_science.user.group.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.protocol.entity.Protocol
import javax.persistence.*

@Entity
class FavoriteGroup (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    var name: String,

    @ManyToOne
    var parent: FavoriteGroup? = null,

    @OneToMany(mappedBy = "parent")
    val subGroups: MutableList<FavoriteGroup>,

    @ManyToMany
    val protocols: MutableList<Protocol>,

    @ManyToMany
    val approaches: MutableList<PublicApproach>
)
