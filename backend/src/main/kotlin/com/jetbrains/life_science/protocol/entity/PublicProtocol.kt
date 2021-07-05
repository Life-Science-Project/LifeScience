package com.jetbrains.life_science.protocol.entity

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.user.master.entity.User
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class PublicProtocol(
    id: Long,
    name: String,
    approach: Approach,
    sections: MutableList<Section>,
    owner: User,

    var rating: Long,

    @ManyToMany
    var coAuthors: MutableList<User>

) : Protocol(id, name, approach, sections, owner)
