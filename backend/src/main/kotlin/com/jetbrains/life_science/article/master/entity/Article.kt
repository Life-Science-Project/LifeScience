package com.jetbrains.life_science.article.master.entity

import com.jetbrains.life_science.category.entity.Category
import javax.persistence.*

@Entity
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    var category: Category,
)
