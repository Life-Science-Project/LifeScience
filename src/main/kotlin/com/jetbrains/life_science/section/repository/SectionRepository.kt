package com.jetbrains.life_science.section.repository

import com.jetbrains.life_science.section.entity.Section
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SectionRepository : JpaRepository<Section, Long>
