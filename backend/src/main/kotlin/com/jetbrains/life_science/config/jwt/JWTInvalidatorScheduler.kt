package com.jetbrains.life_science.config.jwt

import com.jetbrains.life_science.config.jwt.service.ForbiddenJWTService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
@EnableScheduling
class JWTInvalidatorScheduler(val service: ForbiddenJWTService) {
    private val day = 86400000

    @Scheduled(fixedRate = 3600000)
    fun deleteExtraTokens() {
        val currentDate = Date()
        service.findAll().map { token ->
            if (currentDate.time - token.dateTime.time > day) {
                service.delete(token)
            }
        }
    }
}
