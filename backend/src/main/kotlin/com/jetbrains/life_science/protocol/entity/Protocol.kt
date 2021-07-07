package com.jetbrains.life_science.protocol.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Protocol(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    var name: String,

    @ManyToOne
    var approach: PublicApproach,

    @OneToMany
    var sections: MutableList<Section>,

    @ManyToOne
    var owner: UserPersonalData
)
