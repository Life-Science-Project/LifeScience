package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.protocol.entity.Protocol
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
class PublicApproach(
    id: Long,
    name: String,
    sections: MutableList<Section>,
    tags: List<String>,
    owner: Credentials,

    @ManyToMany
    var categories: MutableList<Category>,

    @ManyToMany
    var coAuthors: MutableList<UserPersonalData>,

    @OneToMany
    var protocols: MutableList<Protocol>

) : Approach(id, name, sections, tags, owner)
