package com.jetbrains.life_science.replicator.deserializer.credentials

import com.jetbrains.life_science.auth.refresh.repository.RefreshTokenRepository
import com.jetbrains.life_science.controller.auth.dto.NewUserDTO
import com.jetbrains.life_science.controller.auth.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.replicator.enities.CredentialsStorageEntity
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.factory.CredentialsFactory
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import com.jetbrains.life_science.user.credentials.repository.RoleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class CredentialsReplicator(
    private val roleRepository: RoleRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val credentialsRepository: CredentialsRepository,
    private val credentialsFactory: CredentialsFactory,
    private val entityManager: EntityManager
) {

    lateinit var admin: Credentials

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery(
            "alter sequence credentials_seq restart with 1;\n" +
                "alter sequence user_personal_data_seq restart with 1;\n"
        )
            .executeUpdate()
        refreshTokenRepository.deleteAll()
        credentialsRepository.deleteAll()
    }

    @Transactional
    fun createAdmin() {
        val adminRole = roleRepository.findByName("ROLE_ADMIN")
        val dto = NewUserDTO(
            email = "admin@email.ru",
            password = "password",
            firstName = "admin",
            lastName = "admin"
        )
        val adminCredentials = credentialsFactory.createUser(
            NewUserDTOToInfoAdapter(dto),
            mutableListOf(adminRole)
        )
        admin = credentialsRepository.save(adminCredentials)
    }

    @Transactional
    fun replicateData(data: List<CredentialsStorageEntity>) {
        data.forEach { createUser(it) }
    }

    fun createUser(storageEntity: CredentialsStorageEntity) {
        val credentials = credentialsFactory.copyUser(
            storageEntity,
            mutableListOf(roleRepository.findByName(storageEntity.role))
        )
        credentialsRepository.save(credentials)
    }
}
