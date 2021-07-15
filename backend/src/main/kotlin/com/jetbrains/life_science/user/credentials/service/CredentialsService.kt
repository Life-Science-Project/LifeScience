package com.jetbrains.life_science.user.credentials.service

import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.factory.CredentialsFactory
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import com.jetbrains.life_science.user.credentials.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CredentialsService(
    val credentialsRepository: CredentialsRepository,
    val roleRepository: RoleRepository
) : UserDetailsService {

    @Autowired
    lateinit var factory: CredentialsFactory

    override fun loadUserByUsername(email: String): UserDetails {
        return credentialsRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found: $email") }
    }

    fun getByEmail(email: String): Credentials {
        return credentialsRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException("User with email $email not found") }
    }

    fun createUser(info: NewUserInfo): Credentials {
        checkUserNotExistsByEmail(info.email)
        val roles = mutableListOf(roleRepository.findByName("ROLE_USER"))
        val credentials = factory.createUser(info, roles)
        return credentialsRepository.save(credentials)
    }

    fun checkUserNotExistsByEmail(email: String) {
        if (credentialsRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException(email)
        }
    }

    fun getById(id: Long): Credentials {
        return credentialsRepository.findById(id).orElseThrow {
            throw UserNotFoundException("User with id $id not found")
        }
    }
}
