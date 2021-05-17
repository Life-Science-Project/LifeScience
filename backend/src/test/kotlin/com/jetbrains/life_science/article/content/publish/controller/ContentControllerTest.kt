package com.jetbrains.life_science.article.content.publish.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.dto.ContentDTO
import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.view.ContentView
import com.jetbrains.life_science.article.section.search.SectionSearchUnit
import com.jetbrains.life_science.article.version.search.ArticleVersionSearchUnit
import com.jetbrains.life_science.util.mvc.SearchHelper
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
@AutoConfigureMockMvc
internal class ContentControllerTest
    : ControllerTest<ContentDTO, ContentView>(ContentView::class.java) {

    @Autowired
    lateinit var elasticPopulator: ElasticPopulator

    lateinit var searchHelper: SearchHelper

    @PostConstruct
    fun setup() {
        with(elasticPopulator) {
            addPopulator("content", "elastic/content.json", Content::class.java)
            addPopulator("content_version", "elastic/content_version.json", Content::class.java)
            addPopulator("article", "elastic/article.json", ArticleVersionSearchUnit::class.java)
            addPopulator("section", "elastic/section.json", SectionSearchUnit::class.java)
        }
        searchHelper = SearchHelper(mockMvc)
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * Test to get existing published content
     */
    @Test
    fun `get existing content`() {
        // Request for content
        val content = get("123", "/api/articles/versions/sections/1/contents")
        // Preparing expected data
        val expected = ContentView("123", "general info text", mutableListOf())
        assertEquals(expected, content)
    }

    /**
     * Test to get all existing content by published section id
     */
    @Test
    fun `get all content`() {
        // Request for contents
        val json = getRequest("/api/articles/versions/sections/1/contents")
            .andExpect { status { isOk() } }.andReturn().response.contentAsString
        val content = getViewFromJson(json, ContentView::class.java)
        // Preparing expected data
        val expected = ContentView("123", "general info text", mutableListOf())
        assertEquals(expected, content)
    }

    /**
     * Unpublished Content Retrieval Test
     */
    @Test
    fun `get all content version`() {
        // Request for content
        val content = get("4/contents", "/api/articles/versions/sections")
        // Preparing expected data
        val expected = ContentView("0ab", "sample text 123", mutableListOf())
        assertEquals(expected, content)
    }

    /**
     * The test checks if a 404 code is returned when requested from a non-existent version
     */
    @Test
    fun `get for incorrect version`() {
        // Request for content
        assertNotFound(
            "Section",
            getRequest("/api/articles/versions/sections/4000/contents")
        )
    }

    /**
     * An attempt to add content to a section that already contains content. 400 status expected
     */
    @Test
    fun `attempt to add content to a section`() {
        // Request for content
        assertBadRequest(
            "Content is not editable",
            postRequest(ContentDTO(3, "", listOf(), listOf()), "/api/articles/versions/sections/3/contents")
        )
    }

}
