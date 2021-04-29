package com.jetbrains.life_science.category.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.repository.SectionRepository
import com.jetbrains.life_science.article.section.view.SectionView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
class SectionControllerTest :
    ControllerTest<SectionDTO, SectionView>("Section", SectionView::class.java) {

    init {
        apiUrl = "/api/articles/versions/{id}/sections"
    }

    @MockBean
    lateinit var sectionRepository: SectionRepository

    /**
     * Test: admin wants to get all the sections of
     * non-empty article.
     *
     * Result: returns all sections of non-empty ArticleVersion.
     */
    @Test
    internal fun `admin get sections`() {
        val sections = getArticleVersionSections(1)
        assertTrue(sections.isNotEmpty())
        for (section in sections) {
            assertEquals(section.articleVersionId, 1)
        }
    }

    private fun getArticleVersionSections(id: Long): List<SectionView> {
        val sections = mockMvc.get(apiUrl, id)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn().response.contentAsString
        return getViewsFromJson(sections)
    }
}
