package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.factory.UserFactory
import com.jetbrains.life_science.user.master.repository.RoleRepository
import com.jetbrains.life_science.user.master.repository.UserRepository
import com.jetbrains.life_science.user.organisation.service.OrganisationService
import com.jetbrains.life_science.util.email
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal

@Service
class UserServiceImpl(
    val userFactory: UserFactory,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val articleVersionService: ArticleVersionService,
    val organisationService: OrganisationService,
    val userCredentialsService: UserCredentialsService
) : UserService {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow { UserNotFoundException("User not found") }
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun deleteById(id: Long, principal: Principal) {
        val user = getById(id)
        if (!checkAdminAccess(user, principal)) {
            throw AccessDeniedException("You haven't got enough permissions to delete this user account.")
        }
        userRepository.deleteById(id)
    }

    override fun createUser(info: NewUserInfo): User {
        checkUserNotExists(info.email)
        val roles = mutableListOf(roleRepository.findByName("ROLE_USER"))
        val user = userFactory.create(info, roles)
        return userRepository.save(user)
    }

    @Transactional
    override fun updateRefreshToken(token: String, email: String) {
        getByEmail(email).refreshToken = token
    }

    override fun countAll(): Long {
        return userRepository.count()
    }

    @Transactional
    override fun addFavourite(user: User, articleVersionId: Long, principal: Principal): User {
        if (!checkUserAccess(user, principal)) {
            throw AccessDeniedException("You haven't got enough permissions to add this protocol to favourite.")
        }
        val version = articleVersionService.getById(articleVersionId)
        if (!user.favouriteArticles.any { it.id == articleVersionId }) {
            user.favouriteArticles.add(version)
        }
        return user
    }

    @Transactional
    override fun removeFavourite(user: User, articleVersionId: Long, principal: Principal) {
        if (!checkUserAccess(user, principal)) {
            throw AccessDeniedException("You haven't got enough permissions to delete this protocol from favourite.")
        }
        val version = articleVersionService.getById(articleVersionId)
        if (user.favouriteArticles.any { it.id == articleVersionId }) {
            user.favouriteArticles.remove(version)
        }
    }

    @Transactional
    override fun update(info: UpdateDetailsInfo, user: User, principal: Principal): User {
        if (!checkAdminAccess(user, principal)) {
            throw AccessDeniedException("You haven't got enough permissions to edit this user.")
        }
        val organisations = info.organisations.map {
            organisationService.getByName(it) ?: organisationService.create(it)
        }
        return userFactory.setParams(info, organisations, user)
    }

    fun checkUserNotExists(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException("User with email $email already exists.")
        }
    }

    private fun checkUserAccess(user: User, principal: Principal): Boolean {
        val credentials = userCredentialsService.getByEmail(principal.email)
        return user.id == credentials.id
    }

    private fun checkAdminAccess(user: User, principal: Principal): Boolean {
        val credentials = userCredentialsService.getByEmail(principal.email)
        return user.id == credentials.id || credentials.roles.any {
            it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR"
        }
    }
}
