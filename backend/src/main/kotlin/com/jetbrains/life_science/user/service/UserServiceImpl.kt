package com.jetbrains.life_science.user.service

import com.jetbrains.life_science.exception.UserNotFoundException
import com.jetbrains.life_science.user.entity.NewUserInfo
import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.user.factory.UserFactory
import com.jetbrains.life_science.user.repository.RoleRepository
import com.jetbrains.life_science.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val userFactory: UserFactory,
    val roleRepository: RoleRepository
) : UserService {

    override fun saveUser(newUserInfo: NewUserInfo) {
        val user = userFactory.createUser(newUserInfo, mutableListOf(roleRepository.findByName("ROLE_USER")))
        userRepository.save(user)
    }

    override fun getByName(name: String): User {
        return userRepository.findByUsername(name)
            .orElseThrow { UserNotFoundException("user with name $name not found") }
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { UserNotFoundException("user with id $id not found") }
    }
}
