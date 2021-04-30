package com.jetbrains.life_science.config.jwt.service

import com.jetbrains.life_science.config.jwt.entity.ForbiddenJWT
import com.jetbrains.life_science.config.jwt.entity.ForbiddenJWTInfo
import com.jetbrains.life_science.config.jwt.repository.ForbiddenJWTRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ForbiddenJWTServiceImpl(
    private val forbiddenJWTRepository: ForbiddenJWTRepository
) : ForbiddenJWTService {
    override fun findByToken(token: String): ForbiddenJWT {
        return forbiddenJWTRepository.findByToken(token).orElse(null)
    }

    override fun delete(forbiddenJWT: ForbiddenJWT) {
        forbiddenJWTRepository.delete(forbiddenJWT)
    }

    override fun existsByToken(token: String): Boolean {
        return forbiddenJWTRepository.existsByToken(token)
    }

    override fun saveIfNeed(info: ForbiddenJWTInfo) {
        if (!forbiddenJWTRepository.existsByToken(info.token)) {
            forbiddenJWTRepository.save(ForbiddenJWT(0, info.token, Date()))
        }
    }

    override fun findAll(): List<ForbiddenJWT> {
        return forbiddenJWTRepository.findAll()
    }
}
