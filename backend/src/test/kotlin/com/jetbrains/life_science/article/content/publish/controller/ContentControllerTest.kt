package com.jetbrains.life_science.article.content.publish.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.publish.dto.ContentDTO
import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.view.ContentView
import com.jetbrains.life_science.article.section.search.SectionSearchUnit
import com.jetbrains.life_science.article.version.search.ArticleVersionSearchUnit
import com.jetbrains.life_science.util.mvc.SearchHelper
import com.jetbrains.life_science.util.populator.ElasticPopulator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
internal class ContentControllerTest :
    ControllerTest<ContentDTO, ContentView>(ContentView::class.java) {

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
     * An attempt to add content to a section in published version. 400 status expected
     */
    @Test
    fun `attempt to add content to a section in published version`() {
        assertBadRequest(
            "Content is not editable",
            postRequest(ContentDTO(3, "", listOf(), listOf()), "/api/articles/versions/sections/3/contents")
        )
    }

    /**
     * An attempt was made to add content to another user's version. 403 status pending
     */
    @Test
    @WithUserDetails("user")
    fun `attempt to add content to other user's version`() {
        assertForbidden(
            postRequest(
                ContentDTO(7, "my text 123", listOf("ref 1"), listOf("tag 1")),
                "/api/articles/versions/sections/7/contents"
            )
        )
    }

    /**
     * An attempt was made to add content to a section with existing content. 400 status pendingg
     */
    @Test
    fun `attempt to add content to section with content`() {
        assertBadRequest(
            "Content already exists",
            postRequest(
                ContentDTO(9, "my test text", listOf(), listOf()),
                "/api/articles/versions/sections/9/contents"
            )
        )
    }

    /**
     * The test checks to receive a 404 code when trying to add content to a non-existing section
     */
    @Test
    fun `attempt to add content to not-existent section`() {
        assertNotFound(
            "Section",
            postRequest(ContentDTO(300, "", listOf(), listOf()), "/api/articles/versions/sections/300/contents")
        )
    }

    /**
     * Test for getting code 400 when trying to create content in a section with an id that does not match the id in the DTO
     */
    @Test
    fun `attempt create content with wrong ids`() {
        assertBadRequest(
            "Content's section id and request section id doesn't match",
            postRequest(ContentDTO(4, "", listOf(), listOf()), "/api/articles/versions/sections/3/contents")
        )
    }

    /**
     * The test checks the creation of content and receiving it through the GET method
     */
    @Test
    fun `add content to section test`() = runBlocking {
        // Check that there is no content in this section
        assertTrue(
            getRequest("/api/articles/versions/sections/7/contents").andExpect {
                status { isOk() }
            }.andReturn().response.contentAsString.isEmpty()
        )
        // Action
        val createdContent = post(
            ContentDTO(7, "my text 123", listOf("ref 1"), listOf("tag 1")),
            "/api/articles/versions/sections/7/contents"
        )
        // Waiting for test elastic to save
        delay(2000)
        // Get saved content
        val content = get("7/contents", "/api/articles/versions/sections")
        // Prepare excepted data
        val expectedContent = ContentView(createdContent.id, "my text 123", listOf("ref 1"))
        // Check
        assertEquals(expectedContent, createdContent)
        assertEquals(expectedContent, content)
    }

    @Test
    fun `update content test`() = runBlocking {
        // Update content
        val updatedContent = put(
            "0ab", ContentDTO(4, "my text 123", listOf("ref 1"), listOf("tag 1")),
            "/api/articles/versions/sections/4/contents"
        )
        // Waiting for test elastic to save
        delay(2000)
        // Get saved content
        val content = get("4/contents", "/api/articles/versions/sections")
        // Prepare excepted data
        val expectedContent = ContentView(updatedContent.id, "my text 123", listOf("ref 1"))
        // Check
        assertEquals(expectedContent, updatedContent)
        assertEquals(expectedContent, content)
    }

    /**
     * An attempt was made to edit content to a section with existing content. 400 status pending
     */
    @Test
    fun `attempt to edit to section with content`() {
        assertBadRequest(
            "Content already exists",
            putRequest(
                "6ef",
                ContentDTO(9, "my test text", listOf(), listOf()),
                "/api/articles/versions/sections/8/contents"
            )
        )
    }

    /**
     * The test checks to receive a 404 code when trying to edit content to a non-existing section
     */
    @Test
    fun `attempt to edit content to not-existent section`() {
        assertNotFound(
            "Section",
            putRequest("0ab", ContentDTO(300, "", listOf(), listOf()), "/api/articles/versions/sections/300/contents")
        )
    }

    /**
     * Test for getting code 400 when trying to edit content in a section with an id that does not match the id in the DTO
     */
    @Test
    fun `attempt edit content with wrong ids`() {
        assertBadRequest(
            "Content's section id and request section id doesn't match",
            putRequest(
                "6ef",
                ContentDTO(4, "", listOf(), listOf()), "/api/articles/versions/sections/9/contents"
            )
        )
    }

    /**
     * An attempt was made to edit content to another user's version. 403 status pending
     */
    @Test
    @WithUserDetails("user")
    fun `attempt to edit content to other user's version`() {
        assertForbidden(
            putRequest(
                "13rt",
                ContentDTO(7, "my text 123", listOf("ref 1"), listOf("tag 1")),
                "/api/articles/versions/sections/10/contents"
            )
        )
    }

    /**
     * An attempt was made to delete content to another user's version. 403 status pending
     */
    @Test
    fun `attempt to delete content of other user's version`() {
        assertForbidden(
            deleteRequest(
                "13rt",
                "/api/articles/versions/sections/10/contents"
            )
        )
    }

    /**
     * The test checks to receive a 404 code when trying to delete content to a non-existing section
     */
    @Test
    fun `attempt to delete content to not-existent section`() {
        assertNotFound(
            "Section",
            deleteRequest("0ab", "/api/articles/versions/sections/300/contents")
        )
    }

    /**
     * Test for getting code 400 when trying to edit content in a section with an id that does not match the id in the DTO
     */
    @Test
    fun `attempt delete content with wrong ids`() {
        assertBadRequest(
            "Content's section id and request section id doesn't match",
            deleteRequest(
                "6ef",
                "/api/articles/versions/sections/9/contents"
            )
        )
    }

    /**
     * An attempt to delete content from a section in published version. 404 status expected
     */
    @Test
    fun `attempt to delete content from a section in published version`() {
        assertNotFound(
            "Content",
            deleteRequest("123", "/api/articles/versions/sections/1/contents")
        )
    }

    /**
     * An attempt to delete content from a section in published version.
     */
    @Test
    fun `delete content test`() = runBlocking {
        // Check content exists
        `get existing content`()
        // Delete content
        delete("0ab", "/api/articles/versions/sections/4/contents")
        // Waiting for elastic to apply changes
        delay(2000)
        // Check that result is empty so content was deleted
        val json = getRequest("/api/articles/versions/sections/4/contents")
            .andExpect { status { isOk() } }.andReturn().response.contentAsString
        assertTrue(json.isEmpty())
    }
}
