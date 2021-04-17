package com.jetbrains.life_science.user.credentials.service

import com.jetbrains.life_science.exception.UserAlreadyExistsException
import com.jetbrains.life_science.exception.UserNotFoundException
import com.jetbrains.life_science.user.credentials.entity.NewUserInfo
import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.user.credentials.factory.UserCredentialsFactory
import com.jetbrains.life_science.user.credentials.repository.RoleRepository
import com.jetbrains.life_science.user.credentials.repository.UserCredentialsRepository
import org.springframework.stereotype.Service

@Service
class UserCredentialsServiceImpl(
    val userCredentialsRepository: UserCredentialsRepository,
    val userCredentialsFactory: UserCredentialsFactory,
    val roleRepository: RoleRepository
) : UserCredentialsService {

    override fun getByEmail(email: String): UserCredentials {
        return userCredentialsRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException("user with email $email not found") }
    }

    override fun getById(id: Long): UserCredentials {
        return userCredentialsRepository.findById(id)
            .orElseThrow { UserNotFoundException("user with id $id not found") }
    }

    override fun createUser(userInfo: NewUserInfo): UserCredentials {
        checkUserNotExistsByEmail(userInfo.email)
        val roles = mutableListOf(roleRepository.findByName("ROLE_USER"))
        val user = userCredentialsFactory.createUser(userInfo, roles)
        return userCredentialsRepository.save(user)
    }

    fun checkUserNotExistsByEmail(email: String) {
        if (userCredentialsRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException("user with email $email already exists")
        }
    }
}
