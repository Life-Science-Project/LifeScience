package com.jetbrains.life_science.user.organisation.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Organisation(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    val name: String

)
