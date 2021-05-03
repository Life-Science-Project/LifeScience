package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.exception.not_found.UserNotFoundException
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.factory.UserFactory
import com.jetbrains.life_science.user.master.repository.RoleRepository
import com.jetbrains.life_science.user.master.repository.UserRepository
import com.jetbrains.life_science.user.organisation.service.OrganisationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userFactory: UserFactory,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val articleService: ArticleService,
    val organisationService: OrganisationService
) : UserService {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getByEmail(email: String): User {
        return userRepository.getByEmail(email)
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun deleteById(id: Long) {
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

    @Transactional
    override fun addFavourite(user: User, articleId: Long): User {
        val article = articleService.getById(articleId)
        if (!user.favouriteArticles.any { it.id == articleId }) {
            user.favouriteArticles.add(article)
            article.users.add(user)
        }
        return user
    }

    @Transactional
    override fun removeFavourite(user: User, articleId: Long) {
        val article = articleService.getById(articleId)
        if (user.favouriteArticles.any { it.id == articleId }) {
            user.favouriteArticles.remove(article)
            article.users.remove(user)
        }
    }

    @Transactional
    override fun update(info: AddDetailsInfo, user: User): User {
        val organisations = info.organisations.map {
            organisationService.getByName(it) ?: organisationService.create(it)
        }
        return userFactory.setParams(info, organisations, user)
    }

    fun checkUserNotExists(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException("user with email $email already exists")
        }
    }
}
