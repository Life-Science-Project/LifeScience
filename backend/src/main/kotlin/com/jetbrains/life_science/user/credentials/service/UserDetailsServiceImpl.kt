package com.jetbrains.life_science.user.credentials.service

import com.jetbrains.life_science.user.credentials.repository.UserCredentialsRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

@Service
class UserDetailsServiceImpl(val userCredentialsRepository: UserCredentialsRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userCredentialsRepository.findByEmail(email).get()

        val authorities: List<GrantedAuthority> =
            user.roles.map { role -> SimpleGrantedAuthority(role.name) }.toList()

        return User
            .withUsername(email)
            .password(user.password)
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }
}
