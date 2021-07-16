package com.jetbrains.life_science.review.edit_record.entity

import com.jetbrains.life_science.section.entity.Section
import javax.persistence.*

@MappedSuperclass
abstract class EditRecord(
    @OneToMany
    var deletedSections: MutableList<Section>,

    @OneToMany
    var createdSections: MutableList<Section>
) {
    abstract val id: Long
}
