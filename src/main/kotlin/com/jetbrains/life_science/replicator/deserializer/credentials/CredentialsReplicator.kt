package com.jetbrains.life_science.replicator.deserializer.credentials

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import com.jetbrains.life_science.user.credentials.repository.RoleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CredentialsReplicator(
    private val roleRepository: RoleRepository,
    private val credentialsRepository: CredentialsRepository
) {

    lateinit var admin: Credentials

    @Transactional
    fun deleteAll() {
        credentialsRepository.deleteAll()
    }

    @Transactional
    fun createAdmin() {
        val adminRole = roleRepository.findByName("ROLE_ADMIN")
        val rawAdmin = Credentials(
            id = 0,
            email = "admin@email.ru",
            password = "password",
            roles = mutableListOf(adminRole)
        )
        admin = credentialsRepository.save(rawAdmin)
    }
}
