package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import javax.persistence.*

@Entity
class DraftApproach(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override val id: Long,

    name: String,
    sections: MutableList<Section>,
    tags: MutableList<String>,
    owner: Credentials,

    @ManyToMany
    var categories: MutableList<Category>,

    @ManyToMany
    var participants: MutableList<Credentials>

) : Approach(id, name, sections, tags, owner)
