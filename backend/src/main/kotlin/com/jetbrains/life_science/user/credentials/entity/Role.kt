package com.jetbrains.life_science.user.credentials.entity

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String

)
