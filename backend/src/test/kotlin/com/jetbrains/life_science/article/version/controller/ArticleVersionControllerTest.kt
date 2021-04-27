package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
internal class ArticleVersionControllerTest :
    ControllerTest<ArticleVersionDTO, ArticleVersionView>("ArticleVersion", ArticleVersionView::class.java) {

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
        val expectedView = ArticleVersionView("master 1", 1, expectedSectionViews)

        // Action
        val view = get(1, urlWithArticleId(1))

        // Check
        assertEquals(expectedView, view)
    }

    /**
     * Trying to get a version not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    @WithUserDetails("user")
    fun `get article version with wrong user`() {

    }

    /**
     * Trying to get a version with a non-existent identifier.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `get sections wrong section id`() {

    }

    /**
     * The controller should return views of the all available versions.
     */
    @Test
    @Transactional
    fun `get all versions`() {
        // Preparing expected data
        val expectedSectionViews1 = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView1 = ArticleVersionView("master 1", 1, expectedSectionViews1)
        val expectedView2 = ArticleVersionView("version 1.1", 1, listOf())
        val expectedView3 = ArticleVersionView("version 2.1", 1, listOf(SectionLazyView(4, "name 2", 3)))
        val expectedResult = listOf(expectedView1, expectedView2, expectedView3)

        // Action
        val result = getAllVersions(1)

        // Check
        assertEquals(expectedResult.toSet(), result.toSet())
    }


    /**
     * Trying to get a versions not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    fun `get all versions with wrong user`() {

    }

    /**
     * Trying to get versions with a non-existent identifier.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `get all versions with wrong id`() {

    }

    /**
     * An attempt was made to create a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    @Transactional
    fun `create version with with wrong article id`() {

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
        val expectedView = ArticleVersionView("next version", 1, listOf())

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
    fun `update version with with wrong user id`() {

    }

    /**
     * An attempt was made to update a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    @Transactional
    fun `update version with with wrong article id`() {

    }

    /**
     * Article versioning update test
     */
    @Test
    @Transactional
    fun `update version`() {

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
