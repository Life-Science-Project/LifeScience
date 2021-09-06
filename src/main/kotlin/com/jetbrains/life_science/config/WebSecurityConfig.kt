package com.jetbrains.life_science.config

import com.jetbrains.life_science.auth.JWTAuthEntryPoint
import com.jetbrains.life_science.auth.JWTAuthTokenFilter
import org.springframework.beans.factory.annotation.Value
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

    @Value("#{'\${cors.allowed}'.split(',')}")
    lateinit var corsAllowedList: List<String>

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
                "/api/approaches/draft/**",
                "/api/protocols/draft/**",
                "/api/review/request/**",
                "/api/users/**",
                "/api/ping/**"
            ).fullyAuthenticated()
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
        config.allowedMethods = HttpMethod.values().map { value -> value.name }
        config.allowCredentials = true
        config.allowedOrigins = corsAllowedList
        return config
    }
}
