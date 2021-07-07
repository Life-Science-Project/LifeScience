package com.jetbrains.life_science.auth2.refresh.entity

import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class RefreshToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val code: String,

        @OneToOne
        val credentials: Credentials,

        val expirationDateTime: LocalDateTime
)