package com.jetbrains.life_science.section.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Section(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    @Column(name = "order_num")
    var order: Long,

    var visible: Boolean,

    var published: Boolean
)
