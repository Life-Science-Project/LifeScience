package com.jetbrains.life_science.user.data.service

import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import com.jetbrains.life_science.user.data.factory.UserPersonalDataFactory
import com.jetbrains.life_science.user.data.repository.UserPersonalDataRepository
import com.jetbrains.life_science.user.organisation.service.OrganisationService
import org.springframework.stereotype.Service

@Service
class UserPersonalDataServiceImpl(
    val userPersonalDataFactory: UserPersonalDataFactory,
    val userPersonalDataRepository: UserPersonalDataRepository,
    val organisationService: OrganisationService
) : UserPersonalDataService {

    override fun getAllUsers(): List<UserPersonalData> {
        return userPersonalDataRepository.findAll()
    }

    override fun getById(id: Long): UserPersonalData {
        return userPersonalDataRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun deleteById(id: Long) {
        userPersonalDataRepository.deleteById(id)
    }

    override fun countAll(): Long {
        return userPersonalDataRepository.count()
    }

    override fun update(info: UserPersonalDataInfo, userPersonalData: UserPersonalData): UserPersonalData {
        val organisations = organisationService.createListOfOrganizations(info.organisations)
        return userPersonalDataFactory.setParams(info, organisations, userPersonalData)
    }
}
