package com.jetbrains.life_science.user.organisation.service

import com.jetbrains.life_science.user.organisation.entity.Organisation

interface OrganisationService {

    fun getById(id: Long): Organisation

    fun create(organisationName: String): Organisation

    fun getByName(name: String): Organisation?

    fun getAllOrganisations(): List<Organisation>

    fun getOrganisationsByIds(ids: List<Long>): List<Organisation>

    fun countAll(): Long
}
