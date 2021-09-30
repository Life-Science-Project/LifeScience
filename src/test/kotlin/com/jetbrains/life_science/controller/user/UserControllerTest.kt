package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.favorite_group.view.FavoriteGroupShortView
import com.jetbrains.life_science.controller.protocol.view.ProtocolView
import com.jetbrains.life_science.controller.user.dto.UserPersonalDataDTO
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
     * Should return full view of authorized user.
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
     * Should return list with views of user's draft protocols.
     */
    @Test
    fun `get user's draft protocols`() {
        // Prepare data
        val userId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val expectedProtocols = listOf(
            ProtocolView(
                id = 1,
                name = "second protocol",
                approach = ApproachShortView(
                    id = 1,
                    name = "approach 1",
                    creationDate = timeOf(2020, 12, 17),
                    tags = listOf()
                )
            )
        )

        // Action
        val protocols = getViewAuthorized<List<ProtocolView>>("$pathPrefix/$userId/protocols/draft", accessToken)

        // Assert
        assertEquals(expectedProtocols, protocols)
    }

    /**
     * Should throw forbidden operation exception and
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
        // Prepare data
        val userId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val expectedProtocols = listOf(
            ProtocolView(
                id = 1,
                name = "first published",
                approach = ApproachShortView(
                    id = 1,
                    name = "approach 1",
                    creationDate = timeOf(2020, 12, 17),
                    tags = listOf()
                )
            ),
            ProtocolView(
                id = 2,
                name = "second published",
                approach = ApproachShortView(
                    id = 2,
                    name = "approach 2",
                    creationDate = timeOf(2020, 12, 17),
                    tags = listOf()
                )
            )
        )

        // Action
        val protocols = getViewAuthorized<List<ProtocolView>>("$pathPrefix/$userId/protocols/public", accessToken)

        // Assert
        assertEquals(expectedProtocols, protocols)
    }

    /**
     * Should throw forbidden operation exception and
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
        // Prepare data
        val userId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val expectedApproaches = listOf(
            ApproachShortView(
                id = 1,
                name = "first approach",
                creationDate = timeOf(2020, 12, 17),
                tags = listOf()
            )
        )

        // Action
        val approaches = getViewAuthorized<List<ApproachShortView>>(
            "$pathPrefix/$userId/approaches/draft", accessToken
        )

        // Assert
        assertEquals(expectedApproaches, approaches)
    }

    /**
     * Should throw forbidden operation exception and
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
        // Prepare data
        val userId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val expectedApproaches = listOf(
            ApproachShortView(
                id = 1,
                name = "approach 1",
                creationDate = timeOf(2020, 12, 17),
                tags = listOf()
            ),
            ApproachShortView(
                id = 2,
                name = "approach 2",
                creationDate = timeOf(2020, 12, 17),
                tags = listOf()
            ),
            ApproachShortView(
                id = 3,
                name = "approach 3",
                creationDate = timeOf(2020, 12, 17),
                tags = listOf()
            )
        )

        // Action
        val approaches = getViewAuthorized<List<ApproachShortView>>(
            "$pathPrefix/$userId/approaches/public", accessToken
        )

        // Assert
        assertEquals(expectedApproaches, approaches)
    }

    /**
     * Should throw forbidden operation exception and
     * return code 403_000 with no arguments.
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

    /**
     * Should throw unauthorized exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `unauthorized update user data`() {
        // Prepare data & action
        val userId = 1L

        val request = patchRequest("$pathPrefix/$userId/data")
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        assertEquals(403_000, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Should throw forbidden operation exception and
     * return code 403_000 with no arguments.
     */
    @Test
    fun `update user data without enough rights`() {
        // Prepare data & action
        val userDto = UserPersonalDataDTO(
            true, "BACHELOR", listOf(),
            "Some interesting info", null, null
        )
        val userId = 2L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val request = patchRequestAuthorized("$pathPrefix/$userId/data", userDto, accessToken)
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        assertEquals(403_000, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Should update user personal info.
     */
    @Test
    fun `update user data`() {
        // Prepare data
        val userId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val credentials = credentialsService.getByEmail("email@email.ru")

        // Pre-check
        assertNotNull(credentials.userPersonalData)

        // Prepare data
        val userDto = UserPersonalDataDTO(
            true, "BACHELOR", listOf(),
            "Some interesting info", null, null
        )
        val userData = credentials.userPersonalData!!
        val expectedView = UserFullView(
            id = credentials.id,
            email = credentials.email,
            roles = credentials.roles.map { it.name },
            personalData = UserPersonalDataView(
                firstName = userData.firstName,
                lastName = userData.lastName,
                orcid = userDto.orcid ?: "",
                researchId = userDto.researchId ?: "",
                organisations = listOf(),
                about = userDto.about ?: "",
                doctorDegree = userDto.doctorDegree,
                academicDegree = AcademicDegree.BACHELOR,
                favoriteGroup = FavoriteGroupShortView(
                    id = 1,
                    name = "Favorite"
                )
            )
        )

        // Action
        patchAuthorized<UserFullView>("$pathPrefix/$userId/data", userDto, accessToken)
        val userFullView = getViewAuthorized<UserFullView>("$pathPrefix/current", accessToken)

        // Assert
        assertEquals(expectedView, userFullView)
    }

    /**
     * Should throw degree not found exception
     * and return 404008 code with wrong degree name
     * in arguments.
     */
    @Test
    fun `update data with wrong academic degree`() {
        // Prepare data
        val userId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val userDto = UserPersonalDataDTO(
            true, "BACHELOR239", listOf(),
            "Some interesting info", null, null
        )

        // Action
        val request = patchRequestAuthorized("$pathPrefix/$userId/data", userDto, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_008, exceptionView.systemCode)
        assertEquals("[${userDto.academicDegree}]", exceptionView.arguments[0][0])
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
