package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.favorite_group.view.FavoriteGroupShortView
import com.jetbrains.life_science.controller.user.view.UserFullView
import com.jetbrains.life_science.controller.user.view.UserPersonalDataView
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import com.jetbrains.life_science.user.degree.AcademicDegree
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/user/user_controller_data.sql"
)
internal class UserControllerTest : ApiTest() {

    val pathPrefix = "/api/users"

    @Autowired
    lateinit var credentialsService: CredentialsService

    /**
     * Should return full view of authorized user
     */
    @Test
    fun `get current user`() {
        // Prepare data
        val accessToken = loginAccessToken("email@email.ru", "password")
        val credentials = credentialsService.getByEmail("email@email.ru")

        // Pre-check
        assertNotNull(credentials.userPersonalData)

        // Prepare data
        val userData = credentials.userPersonalData!!
        val expectedView = UserFullView(
            id = credentials.id,
            email = credentials.email,
            roles = credentials.roles.map { it.name },
            personalData = UserPersonalDataView(
                firstName = userData.firstName,
                lastName = userData.lastName,
                orcid = "",
                researchId = "",
                organisations = listOf(),
                about = "",
                doctorDegree = false,
                academicDegree = AcademicDegree.BACHELOR,
                favoriteGroup = FavoriteGroupShortView(
                    id = 1,
                    name = "Favorite"
                )
            )
        )

        // Action
        val userView = getViewAuthorized<UserFullView>("$pathPrefix/current", accessToken)

        // Assert
        assertEquals(expectedView, userView)
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `unauthorized get current user`() {
        getUnauthorized("current")
    }

    /**
     * Should return list with views of user's draft protocols
     */
    @Test
    fun `get user's draft protocols`() {
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `get user's draft protocols without enough rights`() {
        getWithoutPermission("protocols/draft")
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `unauthorized get user's draft protocols`() {
        val userId = 1L
        getUnauthorized("$userId/protocols/draft")
    }

    /**
     * Should return list with views of user's public protocols
     */
    @Test
    fun `get user's public protocols`() {
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `get user's public protocols without enough rights`() {
        getWithoutPermission("protocols/public")
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `unauthorized get user's public protocols`() {
        val userId = 1L
        getUnauthorized("$userId/protocols/public")
    }

    /**
     * Should return list with views of user's draft approaches
     */
    @Test
    fun `get user's draft approaches`() {
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `get user's draft approaches without enough rights`() {
        getWithoutPermission("approaches/draft")
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `unauthorized get user's draft approaches`() {
        val userId = 1L
        getUnauthorized("$userId/approaches/draft")
    }

    /**
     * Should return list with views of user's draft approaches
     */
    @Test
    fun `get user's public approaches`() {
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.*
     */
    @Test
    fun `get user's public approaches without enough rights`() {
        getWithoutPermission("approaches/public")
    }

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `unauthorized get user's public approaches`() {
        val userId = 1L
        getUnauthorized("$userId/approaches/public")
    }

    private fun getUnauthorized(pathSuffix: String) {
        // Prepare data & action
        val request = getRequest("$pathPrefix/$pathSuffix")
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        assertEquals(403_000, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    private fun getWithoutPermission(pathSuffix: String) {
        // Prepare data & action
        val userId = 2L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val request = getAuthorized("$pathPrefix/$userId/$pathSuffix", accessToken)
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        assertEquals(403_000, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }
}
