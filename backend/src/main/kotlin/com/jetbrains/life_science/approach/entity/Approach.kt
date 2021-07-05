package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.section.entity.Section
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.OneToMany

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
