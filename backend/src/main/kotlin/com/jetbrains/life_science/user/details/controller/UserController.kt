package com.jetbrains.life_science.user.details.controller

import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.details.dto.AddDetailsDTO
import com.jetbrains.life_science.user.details.dto.AddDetailsDTOToInfoAdapter
import com.jetbrains.life_science.user.details.dto.FavouriteDataToInfoAdapter
import com.jetbrains.life_science.user.details.entity.User
import com.jetbrains.life_science.user.details.service.UserService
import com.jetbrains.life_science.user.details.view.UserView
import com.jetbrains.life_science.user.details.view.UserViewMapper
import com.jetbrains.life_science.util.email
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService,
    val userCredentialsService: UserCredentialsService,
    val mapper: UserViewMapper,
    val articleMapper: ArticleViewMapper,
) {

    @GetMapping()
    fun getUsers() {
        TODO("Not yet implemented")
    }

    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId: Long,
    ): UserView {
        val user = userCredentialsService.getById(userId).user
        return mapper.createView(user)
    }

    @PutMapping("/{userId}")
    fun addDetails(
        @Validated @RequestBody addDetailsDTO: AddDetailsDTO,
        @PathVariable userId: Long,
        principal: Principal
    ) {
        val user = userCredentialsService.getById(userId).user
        if (checkAccess(user, principal)) {
            userService.update(AddDetailsDTOToInfoAdapter(addDetailsDTO, user))
        } else {
            throw AccessDeniedException("You haven't got enough permissions to edit this user")
        }
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable userId: Long,
        principal: Principal
    ) {
        val user = userCredentialsService.getById(userId).user
        if (checkAccess(user, principal)) {
            userCredentialsService.delete(userId)
        } else {
            throw AccessDeniedException("You haven't got enough permissions to delete this user")
        }
    }

    @GetMapping("{userId}/favourites/")
    fun getFavourites(@PathVariable userId: Long): List<ArticleView> {
        val user = userService.getById(userId)
        return user.favouriteArticles.map { articleMapper.createView(it) }
    }

    @PutMapping("{userId}/favourites/{articleId}")
    fun addFavourite(
        @PathVariable userId: Long,
        @PathVariable articleId: Long,
        principal: Principal
    ): UserView {
        val user = userService.getById(userId)
        if (checkAccess(user, principal)) {
            val updatedUser = userService.addFavourite(FavouriteDataToInfoAdapter(user, articleId))
            return mapper.createView(updatedUser)
        } else {
            throw AccessDeniedException("You haven't got enough permissions to add this favourite")
        }
    }

    @DeleteMapping("{userId}/favourites/{articleId}")
    fun removeFavourite(
        @PathVariable userId: Long,
        @PathVariable articleId: Long,
        principal: Principal
    ) {
        val user = userService.getById(userId)
        if (checkAccess(user, principal)) {
            userService.removeFavourite(FavouriteDataToInfoAdapter(user, articleId))
        } else {
            throw AccessDeniedException("You haven't got enough permissions to delete this favourite")
        }
    }

    private fun checkAccess(user: User, principal: Principal): Boolean {
        val credentials = userCredentialsService.getByEmail(principal.email)
        return user.id == credentials.user.id || credentials.roles.any {
            it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR"
        }
    }
}
