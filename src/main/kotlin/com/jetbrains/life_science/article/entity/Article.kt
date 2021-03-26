package com.jetbrains.life_science.article.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

)