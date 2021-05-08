package com.jetbrains.life_science.section.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.repository.ContentRepository
import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.parameter.dto.ParameterDTO
import com.jetbrains.life_science.article.section.parameter.view.ParameterView
import com.jetbrains.life_science.article.section.view.SectionView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
class SectionControllerTest :
    ControllerTest<SectionDTO, SectionView>(SectionView::class.java) {

    @MockBean
    lateinit var contentRepository: ContentRepository

    /**
     * Get all the sections of non-empty ArticleVersions.
     */
    @Test
    internal fun `get all sections`() {
        val sections = getArticleVersionSections(1)
        assertTrue(sections.isNotEmpty())
        val firstSection = SectionView(
            id = 1,
            articleVersionId = 1,
            description = "desc 1.1",
            name = "name 1.1",
            contents = null,
            parameters = emptyList(),
            order = 1,
            visible = true
        )
        assertEquals(sections[0], firstSection)
        val secondSection = SectionView(
            id = 2,
            articleVersionId = 1,
            description = "desc 1.2",
            name = "name 1.2",
            contents = null,
            parameters = emptyList(),
            order = 2,
            visible = true
        )
        assertEquals(sections[1], secondSection)
        val thirdSection = SectionView(
            id = 3,
            articleVersionId = 1,
            description = "desc 1.3",
            name = "name 1.3",
            contents = null,
            parameters = emptyList(),
            order = 3,
            visible = false
        )
        assertEquals(sections[2], thirdSection)
    }

    /**
     * Try to get all the sections of non-existing ArticleVersions.
     * Should get status code 404.
     */
    @Test
    internal fun `get all sections of non-existing article`() {
        assertNotFound("Article version", getAllRequest(239))
    }

    /**
     * Should get expected section.
     */
    @Test
    internal fun `get section`() {
        val section = get(1, 3)
        val expectedSection = SectionView(
            id = 3,
            articleVersionId = 1,
            name = "name 1.3",
            description = "desc 1.3",
            contents = null,
            parameters = emptyList(),
            order = 3,
            visible = false
        )
        assertEquals(expectedSection, section)
    }

    /**
     * Get non-existing section of non-empty ArticleVersion.
     * Should get status code 404.
     */
    @Test
    internal fun `get non-existing section`() {
        assertNotFound("Section", getRequest(1, 239))
    }

    /**
     * Get section of non-existing ArticleVersion.
     * Should get status code 404.
     */
    @Test
    internal fun `get section of non-existing articleSection`() {
        assertNotFound("Article version", getRequest(239, 1))
    }

    /**
     * Should create new section.
     */
    @Test
    internal fun `create section`() {
        val sectionDTO = SectionDTO(
            "test_creation", 1, "test_creation",
            emptyList(), 5, true
        )
        val responseSection = post(1, sectionDTO)
        assertNotNull(responseSection.id)
        val savedSection = get(responseSection.articleVersionId, responseSection.id)
        val expectedSection = SectionView(
            id = responseSection.id,
            articleVersionId = sectionDTO.articleVersionId,
            name = sectionDTO.name,
            description = sectionDTO.description,
            contents = responseSection.contents,
            parameters = emptyList(),
            order = sectionDTO.order,
            visible = sectionDTO.visible
        )
        assertEquals(expectedSection, savedSection)
    }

    /**
     * Should get status code 404, because articleVersion doesn't exist.
     */
    @Test
    internal fun `create section with non-existing articleVersion`() {
        val sectionDTO = SectionDTO(
            "test_name", 239, "test_description",
            emptyList(), 5, true
        )
        assertNotFound("Article version", postSectionRequest(239, sectionDTO))
    }

    /**
     * Should get status code 404, because articleVersionId in path
     * and in sectionDTO are different.
     */
    @Test
    internal fun `create section with wrong articleVersionId in path`() {
        val sectionDTO = SectionDTO(
            "test_name", 1, "test_description",
            emptyList(), 5, true
        )
        assertNotFound("Section", postSectionRequest(2, sectionDTO))
    }

    /**
     * Updates existing-section.
     */
    @Test
    internal fun `update existing section`() {
        val parameters = listOf(
            ParameterDTO("First parameter", "default_value"),
            ParameterDTO("Second parameter", "default_value")
        )
        val updateSectionDTO = SectionDTO(
            "update_name", 1, "update_description",
            parameters, 5, true
        )
        val oldSection = get(1, 3)
        val responseSection = put(1, 3, updateSectionDTO)
        assertEquals(3, responseSection.id)
        val updatedSection = get(1, responseSection.id)
        val expectedParameters = listOf(
            ParameterView(
                id = 1,
                name = "First parameter",
                defaultValue = "default_value"
            ),
            ParameterView(
                id = 2,
                name = "Second parameter",
                defaultValue = "default_value"
            ),
        )
        val expectedSection = SectionView(
            id = oldSection.id,
            articleVersionId = updateSectionDTO.articleVersionId,
            name = updateSectionDTO.name,
            description = updateSectionDTO.description,
            contents = responseSection.contents,
            parameters = expectedParameters,
            order = updateSectionDTO.order,
            visible = updateSectionDTO.visible
        )
        assertEquals(expectedSection, updatedSection)
    }

    /**
     * Should get status code 404, because articleVersion doesn't exist.
     */
    @Test
    internal fun `update section of non-existing articleVersion`() {
        val updateSectionDTO = SectionDTO(
            "update_name", 239, "update_description",
            emptyList(), 5, true
        )
        assertNotFound("Article version", putRequest(239, 1, updateSectionDTO))
    }

    /**
     * Should get status code 404, because the section doesn't exist.
     */
    @Test
    internal fun `update non-existing section`() {
        val updateSectionDTO = SectionDTO(
            "update_name", 1, "update_description",
            emptyList(), 5, true
        )
        assertNotFound("Section", putRequest(1, 239, updateSectionDTO))
    }

    /**
     * Should get status code 404, because articleVersionId in path
     * and in sectionDTO are different.
     */
    @Test
    internal fun `update section with wrong articleVersionId in path`() {
        val updateSectionDTO = SectionDTO(
            "update_name", 1, "update_description",
            emptyList(), 5, true
        )
        assertNotFound("Section", postSectionRequest(2, updateSectionDTO))
    }

    /**
     * Delete existing section.
     */
    @Test
    internal fun `delete existing section`() {
        deleteSection(1, 2)
        assertNotFound("Section", getRequest(1, 2))
    }

    /**
     * Should get 404, because section doesn't exist.
     */
    @Test
    internal fun `delete non-existing section`() {
        assertNotFound("Section", deleteSectionRequest(1, 239))
    }

    /**
     * Should get 404, because articleVersion doesn't exist.
     */
    @Test
    internal fun `delete section of non-existing articleVersion`() {
        assertNotFound("Article version", deleteSectionRequest(239, 1))
    }

    /**
     * User has access to the sections only of published ArticleVersions.
     */
    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        val publishedArticleVersionId = 1L
        val notPublishedArticleVersionId = 3L
        assertOk(getAllRequest(publishedArticleVersionId))
        assertOk(getRequest(publishedArticleVersionId, 1))
        assertForbidden(getAllRequest(notPublishedArticleVersionId))
        assertForbidden(getRequest(notPublishedArticleVersionId, 4))
        val sectionDTO = SectionDTO(
            "test_forbidden", 1, "test_forbidden",
            emptyList(), 5, true
        )
        assertForbidden(postSectionRequest(1, sectionDTO))
        assertForbidden(putRequest(1, 1, sectionDTO))
        assertForbidden(deleteSectionRequest(1, 1))
    }

    /**
     * Guests can only access to the sections of published ArticleVersions.
     */
    @Test
    @WithAnonymousUser
    internal fun `anonymous privileges`() {
        val publishedArticleVersionId = 1L
        val notPublishedArticleVersionId = 3L
        assertOk(getAllRequest(publishedArticleVersionId))
        assertOk(getRequest(publishedArticleVersionId, 1))
        assertUnauthenticated(getAllRequest(notPublishedArticleVersionId))
        assertUnauthenticated(getRequest(notPublishedArticleVersionId, 4))
        val sectionDTO = SectionDTO(
            "test_unauthenticated", 1, "test_unauthenticated",
            emptyList(), 5, true
        )
        assertUnauthenticated(postSectionRequest(1, sectionDTO))
        assertUnauthenticated(putRequest(1, 1, sectionDTO))
        assertUnauthenticated(deleteSectionRequest(1, 1))
    }

    private fun getArticleVersionSections(id: Long): List<SectionView> {
        val sections = assertOkAndGetJson(getAllRequest(id))
        return getViewsFromJson(sections)
    }

    private fun getAllRequest(articleId: Long): ResultActionsDsl {
        return mockMvc.get("/api/articles/versions/$articleId/sections")
    }

    private fun get(articleId: Long, sectionID: Long): SectionView {
        return get(sectionID, "/api/articles/versions/$articleId/sections")
    }

    private fun getRequest(articleId: Long, sectionID: Long): ResultActionsDsl {
        return getRequest(sectionID, "/api/articles/versions/$articleId/sections")
    }

    protected fun put(articleId: Long, sectionId: Long, sectionDTO: SectionDTO): SectionView {
        return put(sectionId, sectionDTO, "/api/articles/versions/$articleId/sections")
    }

    private fun putRequest(articleId: Long, sectionId: Long, sectionDTO: SectionDTO): ResultActionsDsl {
        return putRequest(sectionId, sectionDTO, "/api/articles/versions/$articleId/sections")
    }

    private fun post(articleId: Long, sectionDTO: SectionDTO): SectionView {
        return post(sectionDTO, "/api/articles/versions/$articleId/sections")
    }

    private fun postSectionRequest(articleId: Long, sectionDTO: SectionDTO): ResultActionsDsl {
        return postRequest(sectionDTO, "/api/articles/versions/$articleId/sections")
    }

    private fun deleteSection(articleId: Long, sectionId: Long) {
        delete(sectionId, "/api/articles/versions/$articleId/sections")
    }

    private fun deleteSectionRequest(articleId: Long, sectionId: Long): ResultActionsDsl {
        return deleteRequest(sectionId, "/api/articles/versions/$articleId/sections")
    }
}
