package com.jetbrains.life_science.article.master

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class ArticleMasterControllerTest :
    ControllerTest<ArticleDTO, ArticleView>(ArticleView::class.java) {

    init {
        apiUrl = "/api/articles"
    }

    /**
     * Should get expected article
     */
    @Test
    internal fun `get existing article`() {
        // Preparing expected data
        val exceptedArticle = ArticleView(
            id = 1,
            version = ArticleVersionView(
                name = "master 1",
                articleId = 1,
                sections = listOf(
                    SectionLazyView(id = 1, name = "name 1.1", order = 1),
                    SectionLazyView(id = 2, name = "name 1.2", order = 2),
                ),
                state = State.PUBLISHED
            )
        )

        // Action
        val article = get(1)

        // Check
        assertEquals(exceptedArticle, article)
    }

    /**
     * Should get status code 404
     */
    @Test
    internal fun `get non-existent article`() {
        // Request with check
        assertNotFound("Article", getRequest(606))
    }

    /**
     * Should create new article
     */
    @Test
    internal fun `create article`() {
        // Preparing request data
        val articleDTO = ArticleDTO(3)

        // Request with check
        createArticle(articleDTO)
    }

    /**
     * Should get status code 404, because category doesn't exist
     */
    @Test
    internal fun `create article with non-existent category`() {
        // Preparing request data
        val articleDto = ArticleDTO(212)

        // Request with check
        assertNotFound("Category", postRequest(articleDto))
    }

    /**
     * Should update existing article
     */
    @Test
    internal fun `update existing category`() {
        // Preparing request data
        val articleDTO = ArticleDTO(2)

        // Request with check
        updateArticle(1, articleDTO)
    }

    /**
     * Should get 404, because article doesn't exist
     */
    @Test
    internal fun `update non-existent category`() {
        // Preparing request data
        val articleDTO = ArticleDTO(111)

        // Request with check
        assertNotFound("Article", putRequest(100, articleDTO))
    }

    /**
     * Should delete existing article
     */
    @Test
    internal fun `delete existing article`() {
        // Action
        delete(3)

        // Check
        assertNotFound("Article", getRequest(3))
    }

    /**
     * Should get 400, because article isn't full
     */
    @Test
    internal fun `delete not empty existing article`() {
        // Request with check
        assertNotEmpty(deleteRequest(1))
    }

    /**
     * Should get 404, because article doesn't exist
     */
    @Test
    @Transactional
    internal fun `delete non-existent article`() {
        // Request with check
        assertNotFound("Article", deleteRequest(100))
    }

    /**
     * Should forbid access to modifying and allow to creating articles
     */
    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        // Prepare request data
        val articleDto = ArticleDTO(2)

        // Requests with check
        assertOk(getRequest(1))
        assertOk(postRequest(articleDto))
        assertForbidden(putRequest(1, articleDto))
        assertForbidden(deleteRequest(3))
    }

    /**
     * Should forbid access to modifying articles
     */
    @Test
    @WithAnonymousUser
    internal fun `anonymous privileges`() {
        // Prepare request data
        val articleDto = ArticleDTO(2)

        // Requests with check
        assertOk(getRequest(1))
        assertUnauthenticated(postRequest(articleDto))
        assertUnauthenticated(putRequest(1, articleDto))
        assertUnauthenticated(deleteRequest(3))
    }

    private fun createArticle(dto: ArticleDTO) {
        // Action
        val responseArticle = post(dto)

        // Check
        assertNotNull(responseArticle.id)
        assertOk(getRequest(responseArticle.id))
    }

    private fun updateArticle(id: Long, dto: ArticleDTO) {
        // Action
        val responseArticle = put(id, dto)

        // Check
        assertEquals(id, responseArticle.id)
        assertOk(getRequest(id))
    }

    protected fun assertNotEmpty(result: ResultActionsDsl) {
        // Check
        result.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value("Article not empty") }
        }
    }
}
