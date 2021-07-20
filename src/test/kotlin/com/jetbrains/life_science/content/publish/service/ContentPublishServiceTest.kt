package com.jetbrains.life_science.content.publish.service

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.exception.not_found.ContentNotFoundException
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/initial_data.sql")
@Transactional
internal class ContentPublishServiceTest {

    @Autowired
    lateinit var service: ContentService

    @Autowired
    lateinit var contentVersionService: ContentVersionService

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    lateinit var elasticPopulator: ElasticPopulator

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content", "elastic/content.json")
            addPopulator("content_version", "elastic/content_version.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * Should delete existing content by its id
     */
    @Test
    fun `delete existing content`() {
        // Prepare
        val idToDelete = "789"

        // Action
        service.delete(idToDelete)

        // Assert
        assertThrows<ContentNotFoundException>("Content not found by id: $idToDelete") {
            service.findById(idToDelete)
        }
    }

    /**
     * Should throw "Not found exception" on attempt to delete by not existing id
     */
    @Test
    fun `delete not existing content`() {
        // Prepare
        val idToDelete = "123123"

        // Action & Assert
        assertThrows<ContentNotFoundException>("Content not found by id: $idToDelete") {
            service.delete(idToDelete)
        }
    }

    /**
     * Should delete existing content by section id
     */
    @Test
    fun `delete existing content by section id`() {
        // Prepare
        val sectionIdToDelete = 1L

        // Action
        service.deleteBySectionId(sectionIdToDelete)

        // Assert
        val content = service.findBySectionId(sectionIdToDelete)
        assertNull(content)
    }

    /**
     * Should do nothing on attempt to delete by not existing section id
     */
    @Test
    fun `delete not existing content by section id`() {
        // Prepare
        val idToDelete = 666L

        // Action
        service.deleteBySectionId(idToDelete)

        // Prepare
        val content = service.findBySectionId(idToDelete)

        // Assert
        assertNull(content)
    }

    /**
     * Should find existing content by its id
     */
    @Test
    fun `find existing content`() {
        // Prepare
        val expectedId = "123"
        val expected = Content(
            id = expectedId,
            sectionId = 1,
            text = "general info text one",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Action
        val content = service.findById(expectedId)

        // Assert
        assertEquals(expected, content)
    }

    /**
     * Should throw "Not found exception" on attempt to find content by not existing id
     */
    @Test
    fun `find not existing content`() {
        // Prepare
        val expectedId = "123123"

        // Action & Assert
        assertThrows<ContentNotFoundException>("Content not found by id: $expectedId") {
            service.findById(expectedId)
        }
    }

    /**
     * Should find existing content by section id
     */
    @Test
    fun `find existing content by section id`() {
        // Prepare
        val expectedSectionId = 1L
        val expected = Content(
            id = "123",
            sectionId = expectedSectionId,
            text = "general info text one",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Action
        val content = service.findBySectionId(expectedSectionId)

        // Assert
        assertEquals(expected, content)
    }

    /**
     * Should find null, if content with that section id not exist
     */
    @Test
    fun `find not existing content by section id`() {
        // Prepare
        val expectedSectionId = 666L

        // Action
        val content = service.findBySectionId(expectedSectionId)

        // Assert
        assertNull(content)
    }

    /**
     * Should remove existing content using contentVersionService and save using contentService
     */
    @Test
    fun `publish existing content`() {
        // Prepare
        val expectedSectionId = 4L

        // Action
        service.publishBySectionId(expectedSectionId)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Prepare
        val content = service.findBySectionId(expectedSectionId)
        val oldContent = contentVersionService.findBySectionId(expectedSectionId)
        val expected = Content(
            id = content?.id,
            sectionId = expectedSectionId,
            text = "user text 12",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Assert
        assertEquals(expected, content)
        assertNull(oldContent)
    }

    /**
     * Should do nothing, if there is no such content found contentVersionService
     */
    @Test
    fun `publish not existing content`() {
        // Prepare
        val expectedSectionId = 666L

        // Action
        service.publishBySectionId(expectedSectionId)

        // Wait
        Thread.sleep(1000)

        // Assert
        val content = service.findBySectionId(expectedSectionId)
        assertNull(content)
    }
}
