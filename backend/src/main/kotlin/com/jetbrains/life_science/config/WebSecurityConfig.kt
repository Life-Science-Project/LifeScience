package com.jetbrains.life_science.config

import com.jetbrains.life_science.config.jwt.JWTAuthEntryPoint
import com.jetbrains.life_science.config.jwt.JWTAuthTokenFilter
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
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(
                "/api/articles/versions/*/reviews/**",
                "/api/articles/versions/**"
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
}
