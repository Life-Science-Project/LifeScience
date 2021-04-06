package com.jetbrains.life_science.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.MessageSourceAccessor
import java.util.* // ktlint-disable no-wildcard-imports

@Configuration
class MessagesConfig {

    @Bean
    fun messageSourceAccessor(messageSource: MessageSource): MessageSourceAccessor {
        return MessageSourceAccessor(messageSource, Locale.ENGLISH)
    }
}
