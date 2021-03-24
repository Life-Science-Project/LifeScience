package com.jetbrains.life_science.section

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Section(

    @Id
    var id: Long,

    var name: String,

    @ManyToOne
    var parent: Section? = null

)
