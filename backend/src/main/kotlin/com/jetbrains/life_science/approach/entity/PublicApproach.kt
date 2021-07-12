package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import javax.persistence.*

@Entity
class PublicApproach(
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
    var coAuthors: MutableList<UserPersonalData>,

    @OneToMany
    var protocols: MutableList<PublicProtocol>

) : Approach(id, name, sections, tags, owner)
