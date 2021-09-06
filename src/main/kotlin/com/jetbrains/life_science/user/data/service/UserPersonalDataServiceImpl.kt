package com.jetbrains.life_science.user.data.service

import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import com.jetbrains.life_science.user.data.factory.UserPersonalDataFactory
import com.jetbrains.life_science.user.data.repository.UserPersonalDataRepository
import com.jetbrains.life_science.user.organisation.service.OrganisationService
import org.springframework.stereotype.Service

@Service
class UserPersonalDataServiceImpl(
    val factory: UserPersonalDataFactory,
    val repository: UserPersonalDataRepository,
    val organisationService: OrganisationService
) : UserPersonalDataService {

    override fun getByCredentials(credentials: Credentials): UserPersonalData? {
        return repository.findByCredentials(credentials)
    }

    override fun getAllUsers(): List<UserPersonalData> {
        return repository.findAll()
    }

    override fun getById(id: Long): UserPersonalData {
        return repository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun countAll(): Long {
        return repository.count()
    }

    override fun update(info: UserPersonalDataInfo, userPersonalData: UserPersonalData): UserPersonalData {
        val organisations = organisationService.getOrganisationsByIds(info.organisations)
        return factory.setParams(info, organisations, userPersonalData)
    }
}
