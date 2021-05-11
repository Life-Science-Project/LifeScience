package com.jetbrains.life_science.user.controller

import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import com.jetbrains.life_science.user.master.dto.UpdateDetailsDTO
import com.jetbrains.life_science.user.master.dto.UpdateDetailsDTOToInfoAdapter
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.service.UserCredentialsService
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.user.master.view.UserView
import com.jetbrains.life_science.user.master.view.UserViewMapper
import com.jetbrains.life_science.util.email
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService,
    val userCredentialsService: UserCredentialsService,
    val mapper: UserViewMapper,
    val articleVersionViewMapper: ArticleVersionViewMapper
) {

    @Operation(summary = "Returns all users")
    @GetMapping
    fun getUsers(): List<UserView> {
        return userService.getAllUsers()
            .map { mapper.createView(it) }
    }

    @Operation(summary = "Returns user")
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId: Long,
    ): UserView {
        val user = userService.getById(userId)
        return mapper.createView(user)
    }

    @Operation(summary = "Returns current user")
    @GetMapping("/current")
    fun getUser(
        principal: Principal
    ): UserView {
        val user = userService.getByEmail(principal.email)
        return mapper.createView(user)
    }

    // TODO(#141): DTO uses entities and enums, many 500 error possibilities
    @Operation(summary = "Updates existing user")
    @PatchMapping("/{userId}")
    fun updateDetails(
        @PathVariable userId: Long,
        @Validated @RequestBody updateDetailsDTO: UpdateDetailsDTO,
        principal: Principal
    ): UserView {
        val user = userService.getById(userId)
        if (!checkAccess(user, principal)) {
            throw AccessDeniedException("Not enough permissions to edit this user")
        }
        return mapper.createView(userService.update(UpdateDetailsDTOToInfoAdapter(updateDetailsDTO), user))
    }

    @Operation(summary = "Deletes existing user")
    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable userId: Long,
        principal: Principal
    ) {
        val user = userService.getById(userId)
        if (!checkAccess(user, principal)) {
            throw AccessDeniedException("Not enough permissions to delete this user")
        }
        userService.deleteById(userId)
    }

    @Operation(summary = "Returns user's favourite versions")
    @GetMapping("/{userId}/favourites")
    fun getFavourites(@PathVariable userId: Long): List<ArticleVersionView> {
        val user = userService.getById(userId)
        return user.favouriteArticles.map { articleVersionViewMapper.createView(it) }
    }

    @Operation(summary = "Adds version to user's favourites")
    @PatchMapping("/{userId}/favourites/{versionId}")
    fun addFavourite(
        @PathVariable userId: Long,
        @PathVariable versionId: Long,
        principal: Principal
    ): UserView {
        val user = userService.getById(userId)
        if (!checkAccess(user, principal)) {
            throw AccessDeniedException("Not enough permissions to add this favourite")
        }
        val updatedUser = userService.addFavourite(user, versionId)
        return mapper.createView(updatedUser)
    }

    @Operation(summary = "Deletes version to user's favourites")
    @DeleteMapping("/{userId}/favourites/{versionId}")
    fun removeFavourite(
        @PathVariable userId: Long,
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        val user = userService.getById(userId)
        if (!checkAccess(user, principal)) {
            throw AccessDeniedException("You haven't got enough permissions to delete this favourite")
        }
        userService.removeFavourite(user, versionId)
    }

    private fun checkAccess(user: User, principal: Principal): Boolean {
        val credentials = userCredentialsService.getByEmail(principal.email)
        return user.id == credentials.id || credentials.roles.any {
            it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR"
        }
    }
}
