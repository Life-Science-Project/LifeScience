package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.section.entity.Section
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
abstract class EditRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToMany
    var deletedSections: MutableList<Section>,

    @OneToMany
    var createdSections: MutableList<Section>
)
