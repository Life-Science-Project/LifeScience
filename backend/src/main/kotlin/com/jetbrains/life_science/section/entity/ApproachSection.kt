package com.jetbrains.life_science.section.entity

import com.jetbrains.life_science.approach.entity.Approach
import javax.persistence.ManyToOne

class ApproachSection(
    id: Long,
    name: String,
    order: Long,

    @ManyToOne
    var approach: Approach

) : Section(id, name, order)
