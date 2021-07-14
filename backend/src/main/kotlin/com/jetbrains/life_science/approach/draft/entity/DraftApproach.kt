package com.jetbrains.life_science.approach.draft.entity

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class DraftApproach(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override val id: Long,

    name: String,
    sections: MutableList<Section> = mutableListOf(),
    tags: MutableList<String> = mutableListOf(),
    owner: Credentials,
    categories: MutableList<Category> = mutableListOf(),
    creationDate: LocalDateTime,

    @ManyToMany
    var participants: MutableList<Credentials>

) : Approach(name, sections, categories, tags, owner, creationDate)
