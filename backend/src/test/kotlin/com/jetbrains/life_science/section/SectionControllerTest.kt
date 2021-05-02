package com.jetbrains.life_science.section

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.repository.ContentRepository
import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.view.SectionView
import com.jetbrains.life_science.category.dto.CategoryDTO
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
        apiUrl = "/api/articles/versions/{id}/sections"
    }

    val dto = SectionDTO(
        "test_name",
        1,
        "test_description",
        ArrayList(),
        5,
        true
    )

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
     * Should get status code 404
     */
    @Test
    internal fun `get all sections of non-existing article`() {
        assertNotFound("ArticleVersion", getAllRequest(239))
    }

    /**
     * Should get expected section
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
     * Get non-existing section of non-empty ArticleVersion
     * Should get status code 404
     */
    @Test
    internal fun `get non-existing section`() {
        assertNotFound("Section", getRequest(1, 239))
    }

    /**
     * Should create new section
     */
    @Test
    internal fun `create section`() {
        createSection(dto)
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
        assertForbidden(postRequest(1))
        assertForbidden(putRequest(1, 1))
        assertForbidden(deleteRequest(1, 1))
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
        assertUnauthenticated(postRequest(1))
        assertUnauthenticated(putRequest(1, 1))
        assertUnauthenticated(deleteRequest(1, 1))
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
        return mockMvc.get(apiUrl, articleId)
    }

    private fun get(articleId: Long, sectionID: Long): SectionView {
        return getViewFromJson(
            getRequest(articleId, sectionID)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }.andReturn().response.contentAsString
        )
    }

    private fun getRequest(articleId: Long, sectionID: Long): ResultActionsDsl {
        return mockMvc.get("$apiUrl/{sectionId}", articleId, sectionID)
    }

    private fun putRequest(articleId: Long, sectionId: Long): ResultActionsDsl {
        return mockMvc.put("$apiUrl/{sectionId}", articleId, sectionId) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun postRequest(articleId: Long): ResultActionsDsl {
        return mockMvc.post(apiUrl, articleId) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun deleteRequest(articleId: Long, sectionId: Long): ResultActionsDsl {
        return mockMvc.delete("$apiUrl/{sectionId}", articleId, sectionId)
    }

    private fun createSection(dto: SectionDTO) {
        val responseSection = post(dto)
        assertNotNull(responseSection.id)
        val savedSection = get(responseSection.articleVersionId, responseSection.id)
        val expectedSection = SectionView(
            responseSection.id, dto.articleVersionId, dto.name, dto.description,
            null, dto.order, dto.visible
        )
        assertEquals(expectedSection, savedSection)
    }
}
