package com.jetbrains.life_science.user.details.service

import com.jetbrains.life_science.exception.UserNotFoundException
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.details.entity.AddDetailsInfo
import com.jetbrains.life_science.user.details.entity.User
import com.jetbrains.life_science.user.details.factory.UserFactory
import com.jetbrains.life_science.user.details.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userCredentialsService: UserCredentialsService,
    val userFactory: UserFactory,
    val userRepository: UserRepository
) : UserService {

    override fun getByEmail(email: String): User {
        return userCredentialsService.getByEmail(email).user
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun delete(user: User) {
        userRepository.delete(user)
    }

    @Transactional
    override fun update(info: AddDetailsInfo, user: User): User {
        return userFactory.setParams(user, info)
    }
}
