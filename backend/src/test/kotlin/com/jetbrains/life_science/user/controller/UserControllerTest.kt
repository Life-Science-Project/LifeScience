package com.jetbrains.life_science.user.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.master.dto.UpdateDetailsDTO
import com.jetbrains.life_science.user.master.view.UserView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@Transactional
@WithUserDetails("admin")
internal class UserControllerTest :
    ControllerTest<UpdateDetailsDTO, UserView>(UserView::class.java) {

    init {
        apiUrl = "/api/users"
    }

    /**
     * Should get all users
     */
    @Test
    internal fun `get all users`() {
        val users = getAllUsers()
        assertTrue(users.isNotEmpty())
    }

    /**
     * Should get expected user
     */
    @Test
    internal fun `get existing user`() {
        val user = get(1)
        val expectedUser = UserView(
            id = 1,
            email = "admin",
            firstName = "Admin",
            lastName = "Admin-Admin",
            doctorDegree = DoctorDegree.PhD,
            academicDegree = AcademicDegree.ASSOCIATE,
            organisations = listOf(),
            orcid = "123",
            researchId = "222",
            roles = listOf("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR")
        )
        assertEquals(expectedUser, user)
    }

    /**
     * Should get status code 404
     */
    @Test
    internal fun `get non-existent user`() {
        assertNotFound("User", getRequest(100))
    }

    /**
     * Should get expected user
     */
    @Test
    internal fun `get current user`() {
        val currentUser = getCurrentUser()
        val adminUser = get(1)
        assertEquals(adminUser, currentUser)
    }

    /**
     * Should get 401
     */
    @Test
    @WithAnonymousUser
    internal fun `get current user anonymous`() {
        assertUnauthenticated(getCurrentUserRequest())
    }

    /**
     * Should update existing user
     */
    @Test
    internal fun `update existing user`() {
        val updateDetailsDto = UpdateDetailsDTO(
            doctorDegree = DoctorDegree.NONE,
            academicDegree = AcademicDegree.PROFESSIONAL,
            organisations = listOf("abc", "def"),
            orcid = "babla",
            researchId = "5555"
        )
        updateUser(1, updateDetailsDto)
    }

    /**
     * Should get 404, because user doesn't exist
     */
    @Test
    internal fun `update non-existent user`() {
        val updateDetailsDto = UpdateDetailsDTO(
            doctorDegree = DoctorDegree.NONE,
            academicDegree = AcademicDegree.PROFESSIONAL,
            organisations = listOf("abc", "def"),
            orcid = "babla",
            researchId = "5555"
        )
        assertNotFound("User", patchRequest(100, updateDetailsDto))
    }

    /**
     * Should delete existing user
     */
    @Test
    @WithUserDetails("user")
    internal fun `delete existing user`() {
        delete(2)
        assertNotFound("User", getRequest(3))
    }

    /**
     * Should get 404, because user doesn't exist
     */
    @Test
    internal fun `delete non-existent user`() {
        assertNotFound("User", deleteRequest(100))
    }

    /**
     * Should get correct favourites
     */
    @Test
    @WithUserDetails("user")
    internal fun `get favourites`() {
        val favourites = getFavourites(2)
        val expectedIds = listOf<Long>(1, 2)
        val actualIds = favourites.map { it.id }
        assertEquals(expectedIds.size, favourites.size)
        for (expectedId in expectedIds) {
            assertTrue(actualIds.contains(expectedId))
        }
    }

    /**
     * Should correctly add new favourite
     */
    @Test
    internal fun `update favourites`() {
        val userId = 1L
        val articleId = 1L

        val oldFavourites = getFavourites(userId)
        updateFavourites(userId, articleId)
        val newFavourites = getFavourites(userId)
        assertEquals(oldFavourites.size + 1, newFavourites.size)

        val newFavouriteIds = newFavourites.map { it.id }
        assertTrue(newFavouriteIds.contains(articleId))
    }

    /**
     * Should not change, if added article is already in favourites
     */
    @Test
    @WithUserDetails("user")
    internal fun `update same favourites`() {
        val userId = 2L
        val articleId = 2L

        val oldFavourites = getFavourites(userId)
        updateFavourites(userId, articleId)
        val newFavourites = getFavourites(userId)
        assertEquals(oldFavourites, newFavourites)
    }

    /**
     * Should correctly delete from favourites
     */
    @Test
    @WithUserDetails("user")
    internal fun `delete from favourites`() {
        val userId = 2L
        val articleId = 1L

        val oldFavourites = getFavourites(userId)
        deleteFavourite(userId, articleId)
        val newFavourites = getFavourites(userId)
        assertEquals(oldFavourites.size - 1, newFavourites.size)

        val newFavouriteIds = newFavourites.map { it.id }
        assertFalse(newFavouriteIds.contains(articleId))
    }

    /**
     * Should not change, if deleted article isn't in favourites
     */
    @Test
    @WithUserDetails("user")
    internal fun `delete non-existent favourite`() {
        val userId = 2L
        val articleId = 3L

        val oldFavourites = getFavourites(userId)
        deleteFavourite(userId, articleId)
        val newFavourites = getFavourites(userId)
        assertEquals(oldFavourites, newFavourites)
    }

    @Test
    @WithUserDetails("admin")
    internal fun `admin privileges`() {
        val userId = 2L

        assertOk(getAllUsersRequest())
        assertOk(getRequest(userId))
        assertOk(getCurrentUserRequest())

        assertOk(getFavouritesRequest(userId))
        assertOk(updateFavouritesRequest(userId, 3))
        assertOk(deleteFavouriteRequest(userId, 1))

        assertOk(deleteRequest(userId))
        assertNotFound("User", getRequest(userId))
    }

    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        val adminId = 1L
        val userId = 2L

        assertOk(getAllUsersRequest())
        assertOk(getRequest(userId))
        assertOk(getRequest(adminId))
        assertOk(getCurrentUserRequest())

        assertOk(getFavouritesRequest(userId))
        assertOk(getFavouritesRequest(adminId))
        assertOk(updateFavouritesRequest(userId, 3))
        assertForbidden(updateFavouritesRequest(adminId, 3))
        assertOk(deleteFavouriteRequest(userId, 1))
        assertForbidden(deleteFavouriteRequest(adminId, 1))

        assertForbidden(deleteRequest(adminId))
        assertOk(deleteRequest(userId))
        assertNotFound("User", getRequest(userId))
    }

    @Test
    @WithAnonymousUser
    internal fun `anonymous privileges`() {
        val userId = 2L

        assertOk(getAllUsersRequest())
        assertOk(getRequest(userId))
        assertUnauthenticated(getCurrentUserRequest())

        assertOk(getFavouritesRequest(userId))
        assertUnauthenticated(updateFavouritesRequest(userId, 3))
        assertUnauthenticated(deleteFavouriteRequest(userId, 1))

        assertUnauthenticated(deleteRequest(userId))
    }

    private fun updateUser(userId: Long, dto: UpdateDetailsDTO) {
        val oldUser = get(userId)
        val responseUser = patch(userId, dto)
        assertEquals(userId, responseUser.id)
        val responseOrgs = responseUser.organisations.map { it.name }
        assertEquals(dto.organisations.size, responseOrgs.size)
        dto.organisations.forEach { assertTrue(responseOrgs.contains(it)) }
        val updatedUser = get(responseUser.id)
        val expectedUser = UserView(
            id = userId,
            email = oldUser.email,
            firstName = oldUser.firstName,
            lastName = oldUser.lastName,
            doctorDegree = dto.doctorDegree,
            academicDegree = dto.academicDegree,
            organisations = responseUser.organisations,
            orcid = dto.orcid,
            researchId = dto.researchId,
            roles = listOf("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR")
        )
        assertEquals(expectedUser, updatedUser)
    }

    private fun getAllUsers(): List<UserView> {
        val users = assertOkAndGetJson(getAllUsersRequest())
        return getViewsFromJson(users)
    }

    private fun getAllUsersRequest(): ResultActionsDsl {
        return mockMvc.get(apiUrl)
    }

    private fun getCurrentUser(): UserView {
        val user = assertOkAndGetJson(getCurrentUserRequest())
        return getViewFromJson(user)
    }

    private fun getCurrentUserRequest(): ResultActionsDsl {
        return mockMvc.get("$apiUrl/current")
    }

    private fun getFavourites(id: Long): List<ArticleVersionView> {
        val articles = assertOkAndGetJson(getFavouritesRequest(id))
        return getViewsFromJson(articles, ArticleVersionView::class.java)
    }

    private fun getFavouritesRequest(id: Long): ResultActionsDsl {
        return mockMvc.get("$apiUrl/$id/favourites")
    }

    private fun updateFavourites(userId: Long, articleId: Long) {
        updateFavouritesRequest(userId, articleId)
            .andExpect {
                status { isOk() }
            }
    }

    private fun updateFavouritesRequest(userId: Long, articleId: Long): ResultActionsDsl {
        return mockMvc.patch("$apiUrl/$userId/favourites/$articleId")
    }

    private fun deleteFavourite(userId: Long, articleId: Long) {
        deleteFavouriteRequest(userId, articleId)
            .andExpect {
                status { isOk() }
            }
    }

    private fun deleteFavouriteRequest(userId: Long, articleId: Long): ResultActionsDsl {
        return deleteRequest(articleId, "$apiUrl/$userId/favourites")
    }
}
