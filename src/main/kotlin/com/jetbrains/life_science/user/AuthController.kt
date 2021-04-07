package com.jetbrains.life_science.user

import com.jetbrains.life_science.config.jwt.JWTProvider
import com.jetbrains.life_science.config.jwt.JWTResponse
import com.jetbrains.life_science.exceptions.UserAlreadyExistException
import com.jetbrains.life_science.exceptions.UserNotFoundException
import com.jetbrains.life_science.user.dto.LoginDTO
import com.jetbrains.life_science.user.dto.NewUserDTO
import com.jetbrains.life_science.user.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.user.repository.RoleRepository
import com.jetbrains.life_science.user.repository.UserRepository
import com.jetbrains.life_science.user.service.UserServiceImpl
import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(val userService: UserServiceImpl,
                     val authenticationManager: AuthenticationManager,
                     val userRepository: UserRepository,
                     val roleRepository: RoleRepository,
                     val encoder: PasswordEncoder,
                     var jwtProvider: JWTProvider) {


    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginDTO): ResponseEntity<*> {

        val userCandidate: Optional<User> = userRepository.findByUsername(loginRequest.username)

        if (userCandidate.isPresent) {
            val user: User = userCandidate.get()
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt: String = jwtProvider.generateJwtToken(user.username)
            val authorities: List<GrantedAuthority> =
                user.roles!!.map { role -> SimpleGrantedAuthority(role.name) }.toList()
            return ResponseEntity.ok(JWTResponse(jwt, user.username, authorities))
        } else {
            throw UserNotFoundException("User not found!")
        }
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUser: NewUserDTO): ResponseEntity<*> {

        val userCandidate: Optional<User> = userRepository.findByUsername(newUser.username)

        if (!userCandidate.isPresent) {
            if (usernameExists(newUser.username)) {
                throw UserAlreadyExistException("Username is already taken!")
            }
            if (emailExists(newUser.email)) {
                throw UserAlreadyExistException("Email is already in use!")
            }

            // Creating user's account
            userService.saveUser(NewUserDTOToInfoAdapter(newUser, encoder))

            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            throw UserAlreadyExistException("User is already exists!")
        }
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByUsername(email).isPresent
    }

    private fun usernameExists(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

}