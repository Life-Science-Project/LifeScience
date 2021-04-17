package com.jetbrains.life_science.user.position.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Position(

    @Id
    val id: Long,

    val name: String
)
