package com.jetbrains.life_science.container.approach.entity

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class Approach(

    var name: String,

    @OneToMany
    var sections: MutableList<Section>,

    @ManyToMany
    var categories: MutableList<Category>,

    @ElementCollection
    var tags: MutableList<String>,

    @ManyToOne
    var owner: Credentials,

    val creationDate: LocalDateTime
) {
    abstract val id: Long
}
