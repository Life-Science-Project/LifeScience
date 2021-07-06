package com.jetbrains.life_science.user.credentials.service

import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.user.credentials.repository.UserCredentialsRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserCredentialsService(
    val userCredentialsRepository: UserCredentialsRepository,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userCredentialsRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found: $email") }
    }

    fun getByEmail(email: String): UserCredentials {
        return userCredentialsRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException("User with email $email not found") }
    }
}
