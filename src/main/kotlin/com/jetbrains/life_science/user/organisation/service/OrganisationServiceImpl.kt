package com.jetbrains.life_science.user.organisation.service

import com.jetbrains.life_science.user.organisation.entity.Organisation
import com.jetbrains.life_science.user.organisation.factory.OrganisationFactory
import com.jetbrains.life_science.user.organisation.repository.OrganisationRepository
import org.springframework.stereotype.Service

@Service
class OrganisationServiceImpl(
    val repository: OrganisationRepository,
    val factory: OrganisationFactory
) : OrganisationService {

    override fun create(organisationName: String): Organisation {
        val organisation = factory.create(organisationName)
        return repository.save(organisation)
    }

    override fun createListOfOrganizations(organisationNames: List<String>): List<Organisation> {
        return organisationNames.map {
            getByName(it) ?: create(it)
        }
    }

    override fun getByName(name: String): Organisation? {
        return repository.findByName(name)
    }

    override fun getAllOrganisations(): List<Organisation> {
        return repository.findAll()
    }

    override fun countAll(): Long {
        return repository.count()
    }
}
