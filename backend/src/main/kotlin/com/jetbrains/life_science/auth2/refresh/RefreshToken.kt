package com.jetbrains.life_science.auth2.refresh

import com.jetbrains.life_science.user.master.entity.UserCredentials
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class RefreshToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val code: String,

        @OneToOne
        val userCredentials: UserCredentials,

        val expirationDateTime: LocalDateTime
)