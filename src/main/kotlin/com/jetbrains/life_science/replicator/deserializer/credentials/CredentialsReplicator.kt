package com.jetbrains.life_science.replicator.deserializer.credentials

import com.jetbrains.life_science.auth.refresh.repository.RefreshTokenRepository
import com.jetbrains.life_science.replicator.enities.CredentialsStorageEntity
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.factory.CredentialsFactory
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import com.jetbrains.life_science.user.credentials.repository.RoleRepository
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class CredentialsReplicator(
    private val roleRepository: RoleRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val credentialsRepository: CredentialsRepository,
    private val credentialsService: CredentialsService,
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
    fun replicateData(data: List<CredentialsStorageEntity>) {
        data.forEach { createUser(it) }
        admin = credentialsService.getById(1L)
        entityManager.flush()
    }

    fun createUser(storageEntity: CredentialsStorageEntity) {
        val credentials = credentialsFactory.copyUser(
            storageEntity,
            storageEntity.role.map { roleRepository.findByName(it) }.toMutableList()
        )
        credentialsRepository.save(credentials)
    }
}
