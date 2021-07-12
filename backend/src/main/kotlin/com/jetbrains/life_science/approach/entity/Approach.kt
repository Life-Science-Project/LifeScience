package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@MappedSuperclass
abstract class Approach(

    @Id
    val id: Long,

    var name: String,

    @OneToMany
    var sections: MutableList<Section>,

    @ElementCollection
    var tags: MutableList<String>,

    @ManyToOne
    var owner: Credentials
)
