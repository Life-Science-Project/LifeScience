package com.jetbrains.life_science.user.organisation.service

import com.jetbrains.life_science.user.organisation.entity.Organisation

interface OrganisationService {

    fun create(organisationName: String): Organisation

    fun createListOfOrganizations(organisationNames: List<String>): List<Organisation>

    fun getByName(name: String): Organisation?

    fun getAllOrganisations(): List<Organisation>

    fun countAll(): Long
}
