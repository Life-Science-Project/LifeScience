package com.jetbrains.life_science.user.organisation.service

import com.jetbrains.life_science.exception.not_found.OrganisationNotFoundException
import com.jetbrains.life_science.user.organisation.entity.Organisation
import com.jetbrains.life_science.user.organisation.factory.OrganisationFactory
import com.jetbrains.life_science.user.organisation.repository.OrganisationRepository
import org.springframework.stereotype.Service

@Service
class OrganisationServiceImpl(
    val repository: OrganisationRepository,
    val factory: OrganisationFactory
) : OrganisationService {

    override fun getById(id: Long): Organisation {
        return repository.findById(id).orElseThrow {
            OrganisationNotFoundException("Organization with id: $id does not exist")
        }
    }

    override fun create(organisationName: String): Organisation {
        val organisation = factory.create(organisationName)
        return repository.save(organisation)
    }

    override fun getByName(name: String): Organisation? {
        return repository.findByName(name)
    }

    override fun getAllOrganisations(): List<Organisation> {
        return repository.findAll()
    }

    override fun getOrganisationsByIds(ids: List<Long>): List<Organisation> {
        return ids.map { getById(it) }
    }

    override fun countAll(): Long {
        return repository.count()
    }
}
