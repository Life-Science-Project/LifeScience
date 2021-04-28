package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
internal class ArticleVersionControllerTest :
    ControllerTest<ArticleVersionDTO, ArticleVersionView>("Article version", ArticleVersionView::class.java) {

    /**
     * The controller should return a view of the available version.
     */
    @Test
    @Transactional
    fun `get article version`() {
        // Preparing expected data
        val expectedSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleVersionView("master 1", 1, expectedSectionViews, State.PUBLISHED)

        // Action
        val view = get(1, urlWithArticleId(1))

        // Check
        assertEquals(expectedView, view)
    }

    /**
     * Trying to get a version from anonymous user.
     * The controller should return a 401 status code.
     */
    @Test
    @Transactional
    @WithAnonymousUser
    fun `get article version with anonymous user`() {
        assertUnauthenticated(getRequest(3, urlWithArticleId(1)))
    }

    /**
     * Trying to get a version not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    @WithUserDetails("user")
    fun `get article version with wrong user`() {
        assertForbidden(getRequest(3, urlWithArticleId(1)))
    }

    /**
     * Trying to get a version with a non-existent identifier.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `get section wrong section id`() {
        assertNotFound(getRequest(-1, urlWithArticleId(1)))
    }

    /**
     * Getting the versions that belong to the admin.
     * The controller should return views of the all available versions from himself and user users.
     */
    @Test
    @Transactional
    fun `get all versions`() {
        // Preparing expected data
        val expectedSectionViews1 = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView1 = ArticleVersionView("master 1", 1, expectedSectionViews1, State.PUBLISHED)
        val expectedView2 = ArticleVersionView("version 1.1", 1, listOf(), State.EDITING)
        val expectedView3 = ArticleVersionView("version 2.1", 1, listOf(SectionLazyView(4, "name 2", 3)), State.EDITING)
        val expectedView4 = ArticleVersionView("version 4.1", 1, listOf(), State.EDITING)
        val expectedView5 = ArticleVersionView("version 5.1", 1, listOf(), State.EDITING)
        val expectedResult = listOf(expectedView1, expectedView2, expectedView3, expectedView4, expectedView5)

        // Action
        val result = getAllVersions(1)

        // Check
        assertEquals(expectedResult.toSet(), result.toSet())
    }

    /**
     * Getting the versions that belong to the user.
     * The controller should only return versions that belong to the user.
     */
    @Test
    @Transactional
    @WithUserDetails("user")
    fun `get all versions with user`() {
        // Preparing expected data
        val expectedView1 = ArticleVersionView("version 4.1", 1, listOf(), State.EDITING)
        val expectedView2 = ArticleVersionView("version 5.1", 1, listOf(), State.EDITING)
        val expectedResult = listOf(expectedView1, expectedView2)

        // Action
        val result = getAllVersions(1)

        // Check
        assertEquals(expectedResult.toSet(), result.toSet())
    }

    /**
     * Trying to get versions with a non-existent article identifier.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `get all versions with wrong article id`() {
        assertNotFound(getRequest(-1, urlWithArticleId(1)))
    }

    /**
     * An attempt was made to create a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    @Transactional
    fun `create version with with wrong article id`() {
        val dto = ArticleVersionDTO(1000, "test")
        assertNotFound(
            postRequest(dto, urlWithArticleId(1000)),
            "Article not found by id: 1000"
        )
    }

    /**
     * Test for the create case when the identifier in dto does not match the identifier from the path variable.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `create version with with different dto id and path variable id`() {
        val dto = ArticleVersionDTO(1000, "test")
        assertBadRequest(
            postRequest(dto, urlWithArticleId(2000)),
            "ArticleVersion's article id and request article id doesn't match"
        )
    }

    /**
     * Article versioning test
     */
    @Test
    @Transactional
    fun `create version`() {
        // Prepare test data
        val dto = ArticleVersionDTO(1, "next version")

        // Prepare expected result
        val expectedView = ArticleVersionView("next version", 1, listOf(), State.EDITING)

        // Action
        val result = post(dto, urlWithArticleId(1))
        val created = getAllVersions(1).find { it == expectedView }

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, created)
    }

    /**
     * Trying to update a versions not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    @WithUserDetails("user")
    fun `update version with with wrong user id`() {
        val dto = ArticleVersionDTO(1, "test")
        assertForbidden(putRequest(1, dto, urlWithArticleId(1)))
    }

    /**
     * An attempt was made to update a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    @Transactional
    fun `update version with with wrong article id`() {
        val dto = ArticleVersionDTO(-1, "test")
        assertNotFound(putRequest(-1, dto, urlWithArticleId(1)))
    }

    /**
     * Test for the update case when the identifier in dto does not match the identifier from the path variable.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `update version with with different dto id and path variable id`() {
        val dto = ArticleVersionDTO(2000, "test")
        assertBadRequest(
            putRequest(1, dto, urlWithArticleId(1)),
            "Article version id from dto does not matches with id from path variable"
        )
    }

    /**
     * Article versioning update test
     */
    @Test
    @Transactional
    fun `update version`() {
        // Prepare test data
        val dto = ArticleVersionDTO(1, "changed version")

        // Prepare expected result
        val expectedSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleVersionView("changed version", 1, expectedSectionViews, State.PUBLISHED)

        // Action
        val result = put(1, dto, urlWithArticleId(1))
        val updated = getAllVersions(1).find { it.name == "changed version" }

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, updated)
    }

    /**
     * Trying to update a versions not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    fun `delete version by wrong user id`() {

    }

    /**
     * An attempt was made to update a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    @Transactional
    fun `delete version by wrong id`() {

    }

    /**
     * Article deletion test
     */
    @Test
    @Transactional
    fun `delete version`() {

    }

    private fun getAllVersions(articleId: Int): List<ArticleVersionView> {
        val request = mockMvc.get(urlWithArticleId(articleId)).andReturn().response.contentAsString
        return jsonMapper.readValue(request, Array<ArticleVersionView>::class.java).toList()
    }

    private fun urlWithArticleId(articleId: Int): String {
        return "/api/articles/$articleId/versions"
    }

}
