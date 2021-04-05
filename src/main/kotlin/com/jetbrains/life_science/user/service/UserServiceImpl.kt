package com.jetbrains.life_science.user.service

import com.jetbrains.life_science.user.entity.UserInfo
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

    override fun saveUser(userInfo: UserInfo) {
        val user = userFactory.createUser(userInfo, mutableListOf(roleRepository.findByName("ROLE_USER")))
        userRepository.save(user)
    }
}