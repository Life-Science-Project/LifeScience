package com.jetbrains.life_science.auth2.refresh

import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long>