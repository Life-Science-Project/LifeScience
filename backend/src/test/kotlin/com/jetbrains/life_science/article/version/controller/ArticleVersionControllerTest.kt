package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.dto.ContentInnerDTO
import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.view.ArticleFullPageView
import com.jetbrains.life_science.article.section.dto.SectionInnerDTO
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.dto.ArticleVersionFullCreationDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class ArticleVersionControllerTest :
    ControllerTest<ArticleVersionFullCreationDTO, ArticleVersionView>(ArticleVersionView::class.java) {

    init {
        apiUrl = "/api/articles/versions"
    }

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    lateinit var elasticPopulator: ElasticPopulator

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content", "elastic/content.json")
            addPopulator("content_version", "elastic/content_version.json")
            addPopulator("article", "elastic/article.json")
            addPopulator("section", "elastic/section.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * The controller should return a view of the available version.
     */
    @Test
    fun `get article version`() {
        // Preparing expected data
        val expectedSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleVersionView(
            1, "master 1", 1, expectedSectionViews,
            State.PUBLISHED_AS_ARTICLE
        )

        // Action
        val view = get(1)

        // Check
        assertEquals(expectedView, view)
    }

    /**
     * Trying to get a version from anonymous user.
     * The controller should return a 401 status code.
     */
    @Test
    @WithAnonymousUser
    fun `get article version with anonymous user`() {
        assertUnauthenticated(getRequest(3))
    }

    /**
     * Trying to get a version not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @WithUserDetails("user")
    fun `get article version with wrong user`() {
        assertForbidden(getRequest(3))
    }

    /**
     * Trying to get versions with a non-existent articleVersion identifier.
     * The controller should return a 404 status code.
     */
    @Test
    fun `get article version with wrong article version id`() {
        assertNotFound("Article version", getRequest(-1))
    }

    /**
     * Trying to get full article with a non-existent articleVersion identifier.
     * The controller should return a 404 status code.
     */
    @Test
    fun `get full article version by wrong article version id`() {
        assertNotFound("Article version", getRequest(-1, "$apiUrl/completed"))
    }

    /**
     * Trying to get a full version not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @WithUserDetails("user")
    fun `get full article version with wrong user`() {
        assertForbidden(getRequest(3, "$apiUrl/completed"))
    }

    /**
     * Trying to get a full article version from anonymous user.
     * The controller should return a 401 status code.
     */
    @Test
    @WithAnonymousUser
    fun `get full article version with anonymous user`() {
        assertUnauthenticated(getRequest(3, "$apiUrl/completed"))
    }

    /**
     * The controller should return a view of the full article.
     */
    @Test
    fun `get full article version of article published version`() {
        val expectedSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleFullPageView(
            "master 1", 1, 1, expectedSectionViews
        )

        val view = get(1, "$apiUrl/completed")

        assertEquals(expectedView, view)
    }

    /**
     * The controller should return a view of the full article.
     */
    @Test
    fun `get full article version of protocol published version`() {
        val expectedSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleFullPageView(
            "master 1", 1, 1, expectedSectionViews
        )

        val view = get(7, "$apiUrl/completed")

        assertEquals(expectedView, view)
    }

    /**
     * Try to get full article by non-published.
     * Should return BAD_REQUEST status.
     */
    @Test
    fun `get full article version of non-published version`() {
        assertBadRequest("Article version is not published yet", getRequest(2, "$apiUrl/completed"))
    }

    /**
     * An attempt was made to create empty version with an invalid category ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `create empty version with wrong category id`() {
        val dto = ArticleVersionFullCreationDTO(ArticleDTO(1000), "test")
        assertNotFound(
            "Category",
            postRequest(dto)
        )
    }

    /**
     * An attempt was made to create a version's copy with an invalid version ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `copy version with wrong version sample id`() {
        assertNotFound(
            "Published version",
            putCopyRequest(1000)
        )
    }

    /**
     * An attempt was made to create a version's copy with an non-published version ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `copy version with non-published version sample id`() {
        assertNotFound(
            "Published version",
            putCopyRequest(3)
        )
    }

    /**
     * Article versioning test without sections
     */
    @Test
    fun `create version without sections`() {
        // Prepare test data
        val dto = ArticleVersionFullCreationDTO(ArticleDTO(1), "next version")

        // Prepare expected result
        val expectedView = ArticleVersionView(8, "next version", 4, listOf(), State.EDITING)

        // Action
        val result = post(dto)
        val created = get(result.id)

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, created)
    }

    /**
     * Article versioning test with sections and content
     */
    @Test
    fun `create version with sections and content`() {
        // Prepare test data
        val dto = ArticleVersionFullCreationDTO(
            articleDTO = ArticleDTO(1),
            name = "big version",
            sections = listOf(
                SectionInnerDTO(
                    "inner section 1",
                    "desc 1",
                    true,
                    ContentInnerDTO("text", listOf("ref 1"), listOf("tag 1"))
                )
            )
        )

        // Prepare expected result
        val expectedView =
            ArticleVersionView(
                12, "big version", 5,
                listOf(SectionLazyView(10, "inner section 1", 0)), State.EDITING
            )

        // Action
        val result = post(dto)
        val created = get(result.id)

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, created)
    }

    /**
     * Article coping test
     */
    @Test
    fun `create version's copy`() {
        // Prepare test data
        val publishedVersionId = 1L

        // Prepare expected result
        val expectedSectionViews = listOf(
            SectionLazyView(7, "name 1.1", 1),
            SectionLazyView(8, "name 1.2", 2)
        )
        val expectedView = ArticleVersionView(10, "master 1", 1, expectedSectionViews, State.EDITING)

        // Action
        val result = putCopy(publishedVersionId)
        val created = get(result.id)

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, created)
    }

    /**
     * Trying to update a versions not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @WithUserDetails("user")
    fun `update version with with wrong user id`() {
        val dto = ArticleVersionDTO(1, "test")
        assertForbidden(putRequest(1, dto))
    }

    /**
     * An attempt was made to update a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `update version with with wrong article id`() {
        val dto = ArticleVersionDTO(-1, "test")
        assertNotFound("Article version", putRequest(-1, dto))
    }

    /**
     * Article versioning update test
     */
    @Test
    fun `update version`() {
        // Prepare test data
        val dto = ArticleVersionDTO(1, "changed version")

        // Prepare expected result
        val expectedSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleVersionView(
            1, "changed version", 1, expectedSectionViews,
            State.PUBLISHED_AS_ARTICLE
        )

        // Action
        val result = put(1, dto, apiUrl, ArticleVersionView::class.java)
        val updated = get(1)

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, updated)
    }

    /**
     * An attempt to archive an article from anonymous user.
     * The controller should return a 401 status code.
     */
    @Test
    @WithAnonymousUser
    fun `archive with anonymous user`() {
        mockMvc.patch("$apiUrl/1/archive")
            .andExpect { status { isUnauthorized() } }
            .andReturn()
    }

    /**
     * An attempt to publish an article from a user without moderator or administrator rights.
     * The controller should return a 403 status code.
     */
    @Test
    @WithUserDetails("user")
    fun `archive with regular user`() {
        mockMvc.patch("$apiUrl/1/archive")
            .andExpect { status { isForbidden() } }
            .andReturn()
    }

    /**
     * An attempt was made to archive a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `archive article with wrong version id`() {
        mockMvc.patch("$apiUrl/1000/archive")
            .andExpect { status { isNotFound() } }
            .andReturn()
    }

    /**
     * Test for archiving the an unpublished version of the article.
     * Change the state of the version to "Archived".
     */
    @Test
    fun `archive not published article`() {
        // Prepare expected data
        val exceptedToArchiveVersionView = ArticleVersionView(
            2, "version 1.1",
            1, listOf(), State.ARCHIVED
        )

        // Action
        mockMvc.patch("$apiUrl/2/archive")
            .andExpect { status { isOk() } }
            .andReturn()

        // Check query
        val archivedVersion = get(2)

        // Check
        assertEquals(exceptedToArchiveVersionView, archivedVersion)
    }

    /**
     * Test for archiving the version of the article.
     * 1) Create search units for the article, sections and move the old content to the index for versions
     * 2) Change the state of the version to "Archived"
     */
    @Test
    fun `archive article`() {
        // Prepare expected data
        val sectionLazyViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val exceptedToArchiveVersionView = ArticleVersionView(
            1, "master 1", 1,
            sectionLazyViews, State.ARCHIVED
        )

        // Action
        mockMvc.patch("$apiUrl/1/archive")
            .andExpect { status { isOk() } }
            .andReturn()

        // Check query
        val archivedVersion = get(1)

        // Check
        assertEquals(exceptedToArchiveVersionView, archivedVersion)
    }

    /**
     * Create new articleVersion by non-existing articleId.
     * The controller should return a 404 status code.
     */
    @Test
    fun `create articleVersion from non-existing article`() {
        val newProtocolDTO = ArticleVersionFullCreationDTO(
            articleDTO = ArticleDTO(1),
            name = "new version"
        )
        assertNotFound("Article", postRequest(newProtocolDTO, "$apiUrl/article/4"))
    }

    /**
     * User tries to create new articleVersion from existing article.
     * The controller should return a 401 status code.
     */
    @Test
    @WithAnonymousUser
    fun `guest creates articleVersion from existing article`() {
        val newProtocolDTO = ArticleVersionFullCreationDTO(
            articleDTO = ArticleDTO(1),
            name = "new version"
        )
        assertUnauthenticated(postRequest(newProtocolDTO, "$apiUrl/article/1"))
    }

    /**
     * Create new articleVersion for existing article.
     */
    @Test
    fun `create articleVersion without sections from existing article`() {
        val dto = ArticleVersionFullCreationDTO(ArticleDTO(1), "next version")
        val expectedView = ArticleVersionView(11, "next version", 1, listOf(), State.EDITING)

        val result = post(dto, "$apiUrl/article/1")
        val created = get(result.id)

        assertEquals(expectedView, result)
        assertEquals(expectedView, created)
    }

    /**
     * Create new protocol for article.
     */
    @Test
    fun `create articleVersion from existing article`() {
        val newProtocolDTO = ArticleVersionFullCreationDTO(
            articleDTO = ArticleDTO(1),
            name = "new version",
            sections = listOf(
                SectionInnerDTO(
                    "inner section 239",
                    "desc 1",
                    true,
                    ContentInnerDTO("text", listOf("ref 1"), listOf("tag 1"))
                )
            )
        )

        val expectedView = ArticleVersionView(
            9, "new version", 1,
            listOf(SectionLazyView(6, "inner section 239", 0)), State.EDITING
        )
        // Action
        val result = post(newProtocolDTO, "$apiUrl/article/1")
        val created = get(result.id)

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, created)
    }

    private fun putCopy(id: Long): ArticleVersionView {
        val viewJson = putCopyRequest(id)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson, ArticleVersionView::class.java)
    }

    private fun putCopyRequest(id: Long): ResultActionsDsl {
        return mockMvc.put("/api/articles/versions/{id}/copy", id) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }
}
