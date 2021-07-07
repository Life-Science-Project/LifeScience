package com.jetbrains.life_science.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class DraftApproach(
    id: Long,
    name: String,
    sections: MutableList<Section>,
    tags: List<String>,

    @ManyToMany
    var categories: MutableList<Category>,

    @ManyToMany
    var participants: MutableList<UserPersonalData>

) : Approach(id, name, sections, tags)
