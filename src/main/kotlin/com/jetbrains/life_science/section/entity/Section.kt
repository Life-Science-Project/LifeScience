package com.jetbrains.life_science.section.entity

import javax.persistence.* // ktlint-disable no-wildcard-imports

@Entity
class Section(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    @ManyToOne
    var parent: Section? = null,

)
