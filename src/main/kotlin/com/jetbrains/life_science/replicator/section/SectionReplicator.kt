package com.jetbrains.life_science.replicator.section

import com.jetbrains.life_science.section.repository.SectionRepository

class SectionReplicator(
    val sectionRepository: SectionRepository
) {

    fun deleteAll() {
        sectionRepository.deleteAll()
    }

    fun replicateData() {

    }

}