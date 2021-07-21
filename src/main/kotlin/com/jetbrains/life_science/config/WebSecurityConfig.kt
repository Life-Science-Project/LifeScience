package com.jetbrains.life_science.config

import com.jetbrains.life_science.auth.JWTAuthEntryPoint
import com.jetbrains.life_science.auth.JWTAuthTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class WebSecurityConfig(
    var userCredentialsService: UserDetailsService,
    val unauthorizedHandler: JWTAuthEntryPoint,
    val jwtAuthTokenFilter: JWTAuthTokenFilter
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(userCredentialsService)
            .passwordEncoder(bCryptPasswordEncoder())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        http.cors().configurationSource { corsConfig() }
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(
                "/api/articles/versions/*/reviews/**",
                "/api/articles/versions/*/copy",
                "/api/articles/versions/*/approve",
                "/api/articles/versions/*/archive",
                "/api/articles/*/versions",
                "/api/review/request/**",
                "/api/ping/**"
            ).fullyAuthenticated()
            .antMatchers("/api/users/current").fullyAuthenticated()
            .antMatchers(HttpMethod.GET).permitAll()
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/api/search/**").permitAll()
            .antMatchers("/api/**").fullyAuthenticated()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    private fun corsConfig(): CorsConfiguration {
        val config = CorsConfiguration().applyPermitDefaultValues()
        config.allowedMethods = listOf("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
        config.allowCredentials = true
        config.allowedOrigins = listOf("https://jetscience-frontend-git-dev-teptind.vercel.app")
        return config
    }
}
