package com.jetbrains.life_science.config.jwt.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "forbidden_jwt")
class ForbiddenJWT(
    @Id
    @GeneratedValue
    val id: Long,

    val token: String,

    val dateTime: Date
)
