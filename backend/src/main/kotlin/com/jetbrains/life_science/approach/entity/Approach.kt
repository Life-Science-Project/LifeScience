package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.protocol.entity.Protocol
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
abstract class Approach(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    @OneToMany
    var sections: MutableList<Section>,

    @ElementCollection
    var tags: List<String>
)
