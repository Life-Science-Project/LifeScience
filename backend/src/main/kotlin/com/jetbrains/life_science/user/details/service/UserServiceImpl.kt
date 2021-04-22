package com.jetbrains.life_science.user.details.service

import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.exception.ArticleNotFoundException
import com.jetbrains.life_science.exception.UserNotFoundException
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.details.entity.AddDetailsInfo
import com.jetbrains.life_science.user.details.entity.FavouriteInfo
import com.jetbrains.life_science.user.details.entity.User
import com.jetbrains.life_science.user.details.factory.UserFactory
import com.jetbrains.life_science.user.details.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userCredentialsService: UserCredentialsService,
    val userFactory: UserFactory,
    val userRepository: UserRepository,
    val articleService: ArticleService
) : UserService {

    override fun getByEmail(email: String): User {
        return userCredentialsService.getByEmail(email).user
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    override fun delete(user: User) {
        userRepository.delete(user)
    }

    @Transactional
    override fun addFavourite(info: FavouriteInfo): User {
        val article = articleService.getById(info.articleId)
        if (!info.user.favouriteArticles.contains(article)) {
            info.user.favouriteArticles.add(article)
            article.users.add(info.user)
        }
        return info.user
    }

    @Transactional
    override fun removeFavourite(info: FavouriteInfo) {
        val article = articleService.getById(info.articleId)
        if (!info.user.favouriteArticles.contains(article)) {
            throw ArticleNotFoundException("Article not found in favourites")
        } else {
            info.user.favouriteArticles.remove(article)
            article.users.remove(info.user)
        }
    }

    @Transactional
    override fun update(info: AddDetailsInfo): User {
        return userFactory.setParams(info)
    }
}
