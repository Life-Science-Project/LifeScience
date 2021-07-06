package com.jetbrains.life_science.category.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import javax.persistence.*

@Entity
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    @ManyToMany
    val subCategories: MutableList<Category>,

    @ManyToMany
    val approaches: MutableList<PublicApproach>,

    @Column(name = "order_num")
    var order: Long
)
