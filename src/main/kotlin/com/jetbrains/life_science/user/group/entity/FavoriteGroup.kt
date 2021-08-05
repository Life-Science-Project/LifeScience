package com.jetbrains.life_science.user.group.entity

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import javax.persistence.*

@Entity
class FavoriteGroup(

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "favorite_group_seq"
    )
    @SequenceGenerator(
        name = "favorite_group_seq",
        allocationSize = 1
    )
    var id: Long,

    var name: String,

    @ManyToOne
    var parent: FavoriteGroup? = null,

    @OneToMany(mappedBy = "parent")
    val subGroups: MutableList<FavoriteGroup>,

    @ManyToMany
    val protocols: MutableList<PublicProtocol>,

    @ManyToMany
    val approaches: MutableList<PublicApproach>
)
