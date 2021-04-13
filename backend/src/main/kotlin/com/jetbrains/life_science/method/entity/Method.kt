package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.section.entity.Section
import javax.persistence.*

@Entity
class Method(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    var section: Section,

)
