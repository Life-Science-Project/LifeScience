package com.jetbrains.life_science.category.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import java.time.LocalDateTime
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

    @ManyToMany(mappedBy = "subCategories")
    val parents: MutableList<Category>,

    var creationDate: LocalDateTime
)
