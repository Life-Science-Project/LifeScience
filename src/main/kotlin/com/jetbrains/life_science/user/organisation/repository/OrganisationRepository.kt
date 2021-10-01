package com.jetbrains.life_science.user.organisation.repository

import com.jetbrains.life_science.user.organisation.entity.Organisation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganisationRepository : JpaRepository<Organisation, Long> {

    fun existsByName(name: String): Boolean

    fun findByName(name: String): Organisation?
}
