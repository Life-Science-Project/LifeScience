package com.jetbrains.life_science.section.entity

import com.jetbrains.life_science.approach.entity.Approach
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ApproachSection(
    id: Long,
    name: String,
    order: Long,
    visible: Boolean,

    @ManyToOne
    var approach: Approach

) : Section(id, name, order, visible)
