package com.jetbrains.life_science.section

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.repository.ContentRepository
import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.view.SectionView
import com.jetbrains.life_science.article.version.repository.ArticleVersionRepository
import com.jetbrains.life_science.user.master.repository.RoleRepository
import com.jetbrains.life_science.user.master.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
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

    init {
        apiUrl = "/api/articles/versions/"
    }

    @MockBean
    lateinit var contentRepository: ContentRepository

    /**
     * Get all the sections of non-empty ArticleVersions.
     */
    @Test
    internal fun `get all sections`() {
        val sections = getArticleVersionSections(1)
        assertTrue(sections.isNotEmpty())
        for (section in sections) {
            assertEquals(section.articleVersionId, 1)
        }
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
            "test_creation", 1, "test_creation", ArrayList(),
            5, true
        )
        createSection(1, sectionDTO)
    }

    /**
     * Should get status code 404, because articleVersion doesn't exist.
     */
    @Test
    internal fun `create section with non-existing articleVersion`() {
        val sectionDTO = SectionDTO(
            "test_name", 239, "test_description", ArrayList(),
            5, true
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
            "test_name", 1, "test_description", ArrayList(),
            5, true
        )
        assertNotFound("Section", postSectionRequest(2, sectionDTO))
    }

    /**
     * Updates existing-section.
     */
    @Test
    internal fun `update existing section`() {
        val updateSectionDTO = SectionDTO(
            "update_name", 1, "update_description",
            ArrayList(), 5, true
        )
        updateSection(1, 3, updateSectionDTO)
    }

    /**
     * Should get status code 404, because articleVersion doesn't exist.
     */
    @Test
    internal fun `update section of non-existing articleVersion`() {
        val updateSectionDTO = SectionDTO(
            "update_name", 239, "update_description",
            ArrayList(), 5, true
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
            ArrayList(), 5, true
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
            ArrayList(), 5, true
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
        assertOk(getAllRequest(1))
        assertOk(getRequest(1, 1))
        assertForbidden(getAllRequest(2))
        assertForbidden(getRequest(3, 4))
        val sectionDTO = SectionDTO(
            "test_forbidden", 1, "test_forbidden", ArrayList(),
            5, true
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
        assertOk(getAllRequest(1))
        assertOk(getRequest(1, 1))
        assertUnauthenticated(getAllRequest(2))
        assertUnauthenticated(getRequest(3, 4))
        val sectionDTO = SectionDTO(
            "test_unauthenticated", 1, "test_unauthenticated",
            ArrayList(), 5, true
        )
        assertUnauthenticated(postSectionRequest(1, sectionDTO))
        assertUnauthenticated(putRequest(1, 1, sectionDTO))
        assertUnauthenticated(deleteSectionRequest(1, 1))
    }

    private fun getArticleVersionSections(id: Long): List<SectionView> {
        val sections = getAllRequest(id)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn().response.contentAsString
        return getViewsFromJson(sections)
    }

    private fun getAllRequest(articleId: Long): ResultActionsDsl {
        return mockMvc.get("$apiUrl/$articleId/sections")
    }

    private fun get(articleId: Long, sectionID: Long): SectionView {
        return get(sectionID, "$apiUrl/$articleId/sections")
    }

    private fun getRequest(articleId: Long, sectionID: Long): ResultActionsDsl {
        return getRequest(sectionID, "$apiUrl/$articleId/sections")
    }

    protected fun put(articleId: Long, sectionId: Long, sectionDTO: SectionDTO): SectionView {
        return put(sectionId, sectionDTO, "$apiUrl/$articleId/sections")
    }

    private fun putRequest(articleId: Long, sectionId: Long, sectionDTO: SectionDTO): ResultActionsDsl {
        return putRequest(sectionId, sectionDTO, "$apiUrl/$articleId/sections")
    }

    private fun post(articleId: Long, sectionDTO: SectionDTO): SectionView {
        return post(sectionDTO, "$apiUrl/$articleId/sections")
    }

    private fun postSectionRequest(articleId: Long, sectionDTO: SectionDTO): ResultActionsDsl {
        return postRequest(sectionDTO, "$apiUrl/$articleId/sections")
    }

    private fun deleteSection(articleId: Long, sectionId: Long) {
        delete(sectionId, "$apiUrl/$articleId/sections")
    }

    private fun deleteSectionRequest(articleId: Long, sectionId: Long): ResultActionsDsl {
        return deleteRequest(sectionId, "$apiUrl/$articleId/sections")
    }

    private fun createSection(id: Long, dto: SectionDTO) {
        val responseSection = post(id, dto)
        assertNotNull(responseSection.id)
        val savedSection = get(responseSection.articleVersionId, responseSection.id)
        val expectedSection = SectionView(
            responseSection.id, dto.articleVersionId, dto.name, dto.description,
            null, dto.order, dto.visible
        )
        assertEquals(expectedSection, savedSection)
    }

    private fun updateSection(articleId: Long, sectionId: Long, sectionDTO: SectionDTO) {
        val oldSection = get(articleId, sectionId)
        val responseSection = put(articleId, sectionId, sectionDTO)
        assertEquals(sectionId, responseSection.id)
        val updatedSection = get(articleId, responseSection.id)
        val expectedSection = SectionView(
            id = oldSection.id,
            articleVersionId = sectionDTO.articleVersionId,
            name = sectionDTO.name,
            description = sectionDTO.description,
            order = sectionDTO.order,
            contents = null,
            visible = sectionDTO.visible
        )
        assertEquals(expectedSection, updatedSection)
    }
}
