package com.jetbrains.life_science.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class FTPConfig {
    @Value("\${ftp-server}")
    lateinit var server: String

    @Value("\${ftp-username}")
    lateinit var username: String

    @Value("\${ftp-password}")
    lateinit var password: String

    @Value("\${ftp-port}")
    val port: Int = 21
}
