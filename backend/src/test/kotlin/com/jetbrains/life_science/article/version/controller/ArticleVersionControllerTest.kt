package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.repository.ContentRepository
import com.jetbrains.life_science.article.section.search.SectionSearchUnit
import com.jetbrains.life_science.article.section.search.repository.SectionSearchUnitRepository
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.search.ArticleVersionSearchUnit
import com.jetbrains.life_science.article.version.search.repository.ArticleVersionSearchUnitRepository
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.nhaarman.mockitokotlin2.times
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class ArticleVersionControllerTest :
    ControllerTest<ArticleVersionDTO, ArticleVersionView>(ArticleVersionView::class.java) {

    @MockBean
    lateinit var articleVersionSearchUnitRepository: ArticleVersionSearchUnitRepository

    @MockBean
    lateinit var sectionSearchUnitRepository: SectionSearchUnitRepository

    @MockBean
    lateinit var contentRepository: ContentRepository

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
    @WithAnonymousUser
    fun `get article version with anonymous user`() {
        assertUnauthenticated(getRequest(3, urlWithArticleId(1)))
    }

    /**
     * Trying to get a version not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @WithUserDetails("user")
    fun `get article version with wrong user`() {
        assertForbidden(getRequest(3, urlWithArticleId(1)))
    }

    /**
     * Trying to get a version with a non-existent identifier.
     * The controller should return a 404 status code.
     */
    @Test
    fun `get section wrong section id`() {
        assertNotFound("Article version", getRequest(-1, urlWithArticleId(1)))
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
    fun `get all versions with wrong article id`() {
        assertNotFound("Article version", getRequest(-1, urlWithArticleId(1)))
    }

    /**
     * An attempt was made to create a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
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
    fun `create version with with different dto id and path variable id`() {
        val dto = ArticleVersionDTO(1000, "test")
        assertBadRequest(
            "ArticleVersion's article id and request article id doesn't match",
            postRequest(dto, urlWithArticleId(2000)),
        )
    }

    /**
     * Article versioning test
     */
    @Test
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
    fun `update version with with wrong article id`() {
        val dto = ArticleVersionDTO(-1, "test")
        assertNotFound("Article version", putRequest(-1, dto, urlWithArticleId(1)))
    }

    /**
     * Test for the update case when the identifier in dto does not match the identifier from the path variable.
     * The controller should return a 404 status code.
     */
    @Test
    fun `update version with with different dto id and path variable id`() {
        val dto = ArticleVersionDTO(2000, "test")
        assertBadRequest(
            "Article version id from dto does not matches with id from path variable",
            putRequest(1, dto, urlWithArticleId(1)),
        )
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
        val expectedView = ArticleVersionView("changed version", 1, expectedSectionViews, State.PUBLISHED)

        // Action
        val result = put(1, dto, urlWithArticleId(1))
        val updated = getAllVersions(1).find { it.name == "changed version" }

        // Check
        assertEquals(expectedView, result)
        assertEquals(expectedView, updated)
    }

    /**
     * An attempt to publish an article from anonymous user.
     * The controller should return a 401 status code.
     */
    @Test
    @WithAnonymousUser
    fun `approve with anonymous user`() {
        mockMvc.patch(urlWithArticleId(1) + "/1/approve")
            .andExpect { status { isUnauthorized() } }
            .andReturn()
    }

    /**
     * An attempt to publish an article from a user without moderator or administrator rights.
     * The controller should return a 403 status code.
     */
    @Test
    @WithUserDetails("user")
    fun `approve with regular user`() {
        mockMvc.patch(urlWithArticleId(1) + "/1/approve")
            .andExpect { status { isForbidden() } }
            .andReturn()
    }

    /**
     * An attempt was made to approve a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `approve article with wrong version id`() {
        mockMvc.patch(urlWithArticleId(1) + "/1000/approve")
            .andExpect { status { isNotFound() } }
            .andReturn()
    }

    /**
     * The test verifies the correctness of the article publication.
     * Test steps:
     * 1) find the replacement version
     * 2) if it is not equal to the new one, then archive it by moving the content to the repository for versions
     * 3) remove all search units associated with the old version
     * 4) mark the new version as published
     * 5) create search units for the new version
     * 6) remove the content of the new version from the repository for versions and create in the main repositories
     */
    @Test
    fun `approve article`() {
        // Configure mocks
        Mockito.`when`(articleVersionSearchUnitRepository.existsById(1)).thenReturn(true)

        Mockito.`when`(sectionSearchUnitRepository.existsById(1)).thenReturn(true)
        Mockito.`when`(sectionSearchUnitRepository.existsById(2)).thenReturn(true)
        Mockito.`when`(sectionSearchUnitRepository.existsById(3)).thenReturn(true)

        val lastContent = Content(1, "test last text", mutableListOf("b"), mutableListOf("1"))
        Mockito.`when`(contentRepository.findBySectionId(1)).thenReturn(lastContent)

        val content = Content(4, "test new text", mutableListOf("a"), mutableListOf("2"))
        Mockito.`when`(contentVersionRepository.findBySectionId(4)).thenReturn(content)

        // Prepare test data
        val expectedToPublishVersionView =
            ArticleVersionView("version 2.1", 1, listOf(SectionLazyView(4, "name 2", 3)), State.PUBLISHED)
        val sectionLazyViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val exceptedToArchiveVersionView = ArticleVersionView("master 1", 1, sectionLazyViews, State.ARCHIVED)

        // Action
        mockMvc.patch(urlWithArticleId(1) + "/3/approve")
            .andExpect { status { isOk() } }
            .andReturn()

        // Checks
        val publishedVersion = get(3, urlWithArticleId(1))
        val archivedVersion = get(1, urlWithArticleId(1))

        assertEquals(expectedToPublishVersionView, publishedVersion)
        assertEquals(exceptedToArchiveVersionView, archivedVersion)

        // Verify elastic modified
        verifyElasticOperationsAfterApprove(lastContent, content)
    }

    private fun verifyElasticOperationsAfterApprove(
        lastContent: Content,
        content: Content
    ) {
        // Deleting old version search units
        Mockito.verify(articleVersionSearchUnitRepository, times(1)).deleteById(1)
        // Saving new version search units
        Mockito.verify(articleVersionSearchUnitRepository, times(1)).save(ArticleVersionSearchUnit(3, "version 2.1"))
        // Deleting old section search units
        Mockito.verify(sectionSearchUnitRepository, times(1)).deleteById(1)
        Mockito.verify(sectionSearchUnitRepository, times(1)).deleteById(2)
        Mockito.verify(sectionSearchUnitRepository, times(1)).deleteById(3)
        // Creating new section search units
        Mockito.verify(sectionSearchUnitRepository, times(1)).save(SectionSearchUnit(4, "desc 2"))
        // Removing last main content from main index
        Mockito.verify(contentRepository, times(1)).deleteAllBySectionId(1)
        // Saving last main content to versions index
        Mockito.verify(contentVersionRepository, times(1)).save(lastContent)
        // Removing new main content version from versions index
        Mockito.verify(contentVersionRepository, times(1)).deleteBySectionId(4)
        // Saving old content version to versions index
        Mockito.verify(contentVersionRepository, times(1)).save(lastContent)
        // Saving main content version to main index
        Mockito.verify(contentRepository, times(1)).save(content)
    }

    /**
     * The test checks for no action when publishing an already published version
     */
    @Test
    fun `approve already approved article`() {
        // Preparing expected data
        val expectedMainSectionViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val expectedView = ArticleVersionView("master 1", 1, expectedMainSectionViews, State.PUBLISHED)

        // Action
        mockMvc.patch(urlWithArticleId(1) + "/1/approve")
            .andExpect { status { isOk() } }
            .andReturn()
        val mainVersionView = get(1, urlWithArticleId(1))

        // Check
        assertEquals(expectedView, mainVersionView)
    }

    /**
     * Test to publish a version of an article for which a published version does not yet exist
     */
    @Test
    fun `approve article with no master version existent`() {
        val content = Content(5, "test new text", mutableListOf("xx"), mutableListOf("yy"))
        Mockito.`when`(contentVersionRepository.findBySectionId(5)).thenReturn(content)

        // Prepare test data
        val expectedToPublishVersionView =
            ArticleVersionView("version 5.1", 2, listOf(SectionLazyView(5, "name 3", 1)), State.PUBLISHED)

        // Action
        mockMvc.patch(urlWithArticleId(2) + "/6/approve")
            .andExpect { status { isOk() } }
            .andReturn()

        // Checks
        val publishedVersion = get(6, urlWithArticleId(2))

        assertEquals(expectedToPublishVersionView, publishedVersion)
        // Saving new version search units
        Mockito.verify(articleVersionSearchUnitRepository, times(1)).save(ArticleVersionSearchUnit(6, "version 5.1"))
        // Creating new section search units
        Mockito.verify(sectionSearchUnitRepository, times(1)).save(SectionSearchUnit(5, "desc 3"))
        // Removing new main content version from versions repository
        Mockito.verify(contentVersionRepository, times(1)).deleteBySectionId(5)
        // Saving main content version to main repository
        Mockito.verify(contentRepository, times(1)).save(content)
    }

    /**
     * An attempt to archive an article from anonymous user.
     * The controller should return a 401 status code.
     */
    @Test
    @WithAnonymousUser
    fun `archive with anonymous user`() {
        mockMvc.patch(urlWithArticleId(1) + "/1/archive")
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
        mockMvc.patch(urlWithArticleId(1) + "/1/archive")
            .andExpect { status { isForbidden() } }
            .andReturn()
    }

    /**
     * An attempt was made to archive a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    fun `archive article with wrong version id`() {
        mockMvc.patch(urlWithArticleId(1) + "/1000/archive")
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
        val exceptedToArchiveVersionView = ArticleVersionView("version 1.1", 1, listOf(), State.ARCHIVED)

        // Action
        mockMvc.patch(urlWithArticleId(1) + "/2/archive")
            .andExpect { status { isOk() } }
            .andReturn()

        // Check query
        val archivedVersion = get(2, urlWithArticleId(1))

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
        // Configure mocks
        Mockito.`when`(articleVersionSearchUnitRepository.existsById(1)).thenReturn(true)

        Mockito.`when`(sectionSearchUnitRepository.existsById(1)).thenReturn(true)
        Mockito.`when`(sectionSearchUnitRepository.existsById(2)).thenReturn(true)
        Mockito.`when`(sectionSearchUnitRepository.existsById(3)).thenReturn(true)

        val lastContent = Content(1, "test last text", mutableListOf("b"), mutableListOf("1"))
        Mockito.`when`(contentRepository.findBySectionId(1)).thenReturn(lastContent)

        // Prepare expected data
        val sectionLazyViews = listOf(
            SectionLazyView(1, "name 1.1", 1),
            SectionLazyView(2, "name 1.2", 2)
        )
        val exceptedToArchiveVersionView = ArticleVersionView("master 1", 1, sectionLazyViews, State.ARCHIVED)

        // Action
        mockMvc.patch(urlWithArticleId(1) + "/1/archive")
            .andExpect { status { isOk() } }
            .andReturn()

        // Check query
        val archivedVersion = get(1, urlWithArticleId(1))

        // Check
        assertEquals(exceptedToArchiveVersionView, archivedVersion)
        // Deleting old version search units
        Mockito.verify(articleVersionSearchUnitRepository, times(1)).deleteById(1)
        // Deleting old section search units
        Mockito.verify(sectionSearchUnitRepository, times(1)).deleteById(1)
        Mockito.verify(sectionSearchUnitRepository, times(1)).deleteById(2)
        Mockito.verify(sectionSearchUnitRepository, times(1)).deleteById(3)
        // Removing last main content from main index
        Mockito.verify(contentRepository, times(1)).deleteAllBySectionId(1)
        // Saving last main content to versions index
        Mockito.verify(contentVersionRepository, times(1)).save(lastContent)
    }

    private fun getAllVersions(articleId: Int): List<ArticleVersionView> {
        val request = mockMvc.get(urlWithArticleId(articleId)).andReturn().response.contentAsString
        return jsonMapper.readValue(request, Array<ArticleVersionView>::class.java).toList()
    }

    private fun urlWithArticleId(articleId: Int): String {
        return "/api/articles/$articleId/versions"
    }
}
