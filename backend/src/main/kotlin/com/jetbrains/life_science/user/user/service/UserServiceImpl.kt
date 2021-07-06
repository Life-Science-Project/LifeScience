package com.jetbrains.life_science.user.user.service

import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import com.jetbrains.life_science.user.user.entity.User
import com.jetbrains.life_science.user.user.factory.UserFactory
import com.jetbrains.life_science.user.user.repository.RoleRepository
import com.jetbrains.life_science.user.user.repository.UserRepository
import com.jetbrains.life_science.user.organisation.service.OrganisationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userFactory: UserFactory,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val organisationService: OrganisationService
) : UserService {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow { UserNotFoundException("User not found") }
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

    override fun createUser(info: NewUserInfo): User {
        checkUserNotExists(info.email)
        val roles = mutableListOf(roleRepository.findByName("ROLE_USER"))
        val user = userFactory.create(info, roles)
        return userRepository.save(user)
    }

    @Transactional
    override fun updateRefreshToken(token: String, email: String) {
        getByEmail(email).refreshToken = token
    }

    override fun countAll(): Long {
        return userRepository.count()
    }

    override fun update(info: UpdateDetailsInfo, user: User): User {
        val organisations = organisationService.createListOfOrganizations(info.organisations)
        return userFactory.setParams(info, organisations, user)
    }

    fun checkUserNotExists(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException("User with email $email already exists.")
        }
    }
}
