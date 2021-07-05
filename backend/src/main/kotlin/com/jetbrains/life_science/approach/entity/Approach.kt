package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.protocol.entity.Protocol
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
class Approach(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    @ManyToMany
    var categories: MutableList<Category>,

    @OneToMany
    var sections: MutableList<Section>,

    @OneToMany
    var protocols: MutableList<Protocol>,

    @ElementCollection
    var tags: List<String>
)
