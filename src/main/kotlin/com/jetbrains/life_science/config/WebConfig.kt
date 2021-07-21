package com.jetbrains.life_science.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/{x:[\\w\\-]+}")
            .setViewName("forward:/")

        registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}")
            .setViewName("forward:/")

        registry.addViewController("/{x:^(?!swagger-ui$).*$}/**/{y:[\\w\\-]+}")
            .setViewName("forward:/")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("https://jetscience-frontend-git-dev-teptind.vercel.app/**")
            .allowedMethods("*")
    }

}
