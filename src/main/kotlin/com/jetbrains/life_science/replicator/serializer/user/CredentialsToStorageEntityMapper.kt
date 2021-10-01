package com.jetbrains.life_science.replicator.serializer.user

import com.jetbrains.life_science.replicator.enities.CredentialsStorageEntity
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class CredentialsToStorageEntityMapper(
    private val credentialsRepository: CredentialsRepository,
    val userDataToStorageEntityMapper: UserDataToStorageEntityMapper
) {
    fun getStorageEntities(): List<CredentialsStorageEntity> {
        return credentialsRepository.findAll(Sort.by("id")).map { createStorageEntity(it) }
    }

    private fun createStorageEntity(credentials: Credentials): CredentialsStorageEntity {
        return CredentialsStorageEntity(
            id = credentials.id,
            email = credentials.email,
            password = credentials.password,
            role = credentials.roles.map { it.name },
            userData = userDataToStorageEntityMapper.getStorageEntity(credentials.userPersonalData)
        )
    }
}
