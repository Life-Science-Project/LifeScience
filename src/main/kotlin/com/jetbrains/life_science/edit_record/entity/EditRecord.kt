package com.jetbrains.life_science.edit_record.entity

import com.jetbrains.life_science.section.entity.Section
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass
import javax.persistence.OneToMany

@MappedSuperclass
abstract class EditRecord(

    var lastEditDate: LocalDateTime,

    @OneToMany
    var deletedSections: MutableList<Section>,

    @OneToMany
    var createdSections: MutableList<Section>

) {
    abstract val id: Long
}
