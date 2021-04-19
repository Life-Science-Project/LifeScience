package com.jetbrains.life_science.article.section.parameters.entity

import com.jetbrains.life_science.article.section.entity.Section
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Parameters(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var name: String,

    @OneToOne
    var section: Section,
)
