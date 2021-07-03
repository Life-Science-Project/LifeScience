package com.jetbrains.life_science.article.primary.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.article.primary.dto.ArticleDTO
import com.jetbrains.life_science.article.primary.view.ArticleView
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class ArticleMasterControllerTest :
    ControllerTest<ArticleDTO, ArticleView>(ArticleView::class.java) {

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    init {
        apiUrl = "/api/articles"
    }

    /**
     * Getting the versions that belong to the user.
     * The controller should only return versions that belong to the user.
     */
    @Test
    @WithUserDetails("user")
    fun `get all versions with user`() {
        // Preparing expected data
        val expectedView1 = ArticleVersionView(4, "version 4.1", 1, listOf(), State.PENDING_FOR_REVIEW)
        val expectedView2 = ArticleVersionView(5, "version 5.1", 1, listOf(), State.EDITING)
        val expectedResult = listOf(expectedView1, expectedView2)

        // Action
        val result = getAllVersions(1)

        // Check
        assertEquals(expectedResult.toSet(), result.toSet())
    }

    /**
     * Getting the versions that belong to the admin.
     * The controller should return views of the all available versions from himself and user users.
     */
    @Test
    fun `get all versions`() {
        // Preparing expected data
        val expectedSectionViews1 = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView1 = ArticleVersionView(1, "master 1", 1, expectedSectionViews1, State.PUBLISHED_AS_ARTICLE)
        val expectedView2 = ArticleVersionView(2, "version 1.1", 1, listOf(), State.EDITING)
        val expectedView3 =
            ArticleVersionView(3, "version 2.1", 1, listOf(SectionLazyView(4, "name 2", 3)), State.EDITING)
        val expectedView4 = ArticleVersionView(4, "version 4.1", 1, listOf(), State.PENDING_FOR_REVIEW)
        val expectedView5 = ArticleVersionView(5, "version 5.1", 1, listOf(), State.EDITING)
        val protocolView = ArticleVersionView(
            7,
            "version 1.2",
            1,
            listOf(SectionLazyView(6, "name 4", 1)),
            State.PUBLISHED_AS_PROTOCOL
        )
        val expectedResult =
            listOf(expectedView1, expectedView2, expectedView3, expectedView4, expectedView5, protocolView)

        // Action
        val result = getAllVersions(1)

        // Check
        assertEquals(expectedResult.toSet(), result.toSet())
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
                id = 1,
                name = "master 1",
                articleId = 1,
                sections = listOf(
                    SectionLazyView(id = 1, name = "name 1.1", order = 1),
                    SectionLazyView(id = 2, name = "name 1.2", order = 2),
                ),
                state = State.PUBLISHED_AS_ARTICLE,
            ),
            protocols = listOf(
                ArticleVersionView(
                    7,
                    "version 1.2",
                    1,
                    listOf(SectionLazyView(6, "name 4", 1)),
                    State.PUBLISHED_AS_PROTOCOL
                )
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
        val articleDTO = ArticleDTO(listOf(2, 3))

        // Request with check
        createArticle(articleDTO)
    }

    /**
     * Should get status code 404, because category doesn't exist
     */
    @Test
    internal fun `create article with non-existent category`() {
        // Preparing request data
        val articleDto = ArticleDTO(listOf(212))

        // Request with check
        assertNotFound("Category", postRequest(articleDto))
    }

    /**
     * Should update existing article
     */
    @Test
    internal fun `update existing category`() {
        // Preparing request data
        val articleDTO = ArticleDTO(listOf(2, 3))

        // Request with check
        updateArticle(1, articleDTO)
    }

    /**
     * Should get 404, because article doesn't exist
     */
    @Test
    internal fun `update non-existent category`() {
        // Preparing request data
        val articleDTO = ArticleDTO(listOf(2, 3, 111))

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
        val articleDto = ArticleDTO(listOf(2, 3))

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
        val articleDto = ArticleDTO(listOf(2, 3))

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

    private fun getAllVersions(articleId: Long): List<ArticleVersionView> {
        val request = mockMvc.get("/api/articles/$articleId/versions").andReturn().response.contentAsString
        return getViewsFromJson(request, ArticleVersionView::class.java)
    }
}
