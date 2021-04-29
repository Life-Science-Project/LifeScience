package com.jetbrains.life_science.article.master

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import org.junit.jupiter.api.Assertions.*
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
        val article = get(1)
        val exceptedArticle = ArticleView(
            id = 1,
            version = ArticleVersionView(
                name = "master 1",
                articleId = 1,
                sections = listOf(
                    SectionLazyView(
                        id = 1,
                        name = "name 1.1"
                    )
                )
            )
        )
        assertEquals(exceptedArticle, article)
    }

    /**
     * Should get status code 404
     */
    @Test
    internal fun `get non-existent article`() {
        assertNotFound("Article", getRequest(606))
    }

    /**
     * Should create new article
     */
    @Test
    internal fun `create article`() {
        val articleDTO = ArticleDTO(3)
        createArticle(articleDTO)
    }

    /**
     * Should get status code 404, because category doesn't exist
     */
    @Test
    internal fun `create article with non-existent category`() {
        val articleDto = ArticleDTO(212)
        assertNotFound("Category", postRequest(articleDto))
    }

    /**
     * Should update existing article
     */
    @Test
    internal fun `update existing category`() {
        val articleDTO = ArticleDTO(2)
        updateArticle(1, articleDTO)
    }

    /**
     * Should get 404, because article doesn't exist
     */
    @Test
    internal fun `update non-existent category`() {
        val articleDTO = ArticleDTO(111)
        assertNotFound("Article", putRequest(100, articleDTO))
    }

    /**
     * Should delete existing article
     */
    @Test
    internal fun `delete existing article`() {
        delete(3)
        assertNotFound("Article", getRequest(3))
    }

    /**
     * Should get 400, because article isn't full
     */
    @Test
    internal fun `delete not empty existing article`() {
        assertNotEmpty(deleteRequest(1))
    }

    /**
     * Should get 404, because article doesn't exist
     */
    @Test
    @Transactional
    internal fun `delete non-existent article`() {
        assertNotFound("Article", deleteRequest(100))
    }

    /**
     * Should forbid access to modifying and allow to creating articles
     */
    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        val articleDto = ArticleDTO(2)

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
        val articleDto = ArticleDTO(2)
        assertOk(getRequest(1))
        assertUnauthenticated(postRequest(articleDto))
        assertUnauthenticated(putRequest(1, articleDto))
        assertUnauthenticated(deleteRequest(3))
    }

    private fun createArticle(dto: ArticleDTO) {
        val responseArticle = post(dto)
        assertNotNull(responseArticle.id)
        get(responseArticle.id)
    }

    private fun updateArticle(id: Long, dto: ArticleDTO) {
        val responseArticle = put(id, dto)
        assertEquals(id, responseArticle.id)
    }

    protected fun assertNotEmpty(result: ResultActionsDsl) {
        result.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value("Article not empty") }
        }
    }
}
