package com.jetbrains.life_science.section.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Section(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotBlank
    var name: String,

    @ManyToOne
    var parent: Section? = null,

    )
