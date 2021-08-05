package com.jetbrains.life_science.content.version.service

import com.jetbrains.life_science.content.maker.makeContentInfo
import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.exception.not_found.ContentNotFoundException
import com.jetbrains.life_science.exception.request.ContentAlreadyExistsException
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
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
internal class ContentVersionServiceTest {

    @Autowired
    lateinit var service: ContentVersionService

    @Autowired
    lateinit var contentService: ContentService

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
     * Should create new content
     */
    @Test
    fun `create content`() {
        // Prepare
        val info = makeContentInfo(
            sectionId = 15,
            text = "new content text",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Action
        val content = service.create(info)

        // Prepare
        val expected = Content(
            id = content.id,
            sectionId = 15,
            text = "new content text",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Assert
        assertNotEquals(String(), content.id)
        assertEquals(expected, content)
    }

    /**
     * Should throw ContentAlreadyExistsException, because content with that section id already exist
     */
    @Test
    fun `create content with existing sectionId`() {
        // Prepare
        val info = makeContentInfo(
            sectionId = 4,
            text = "new content text",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Action & Assert
        assertThrows<ContentAlreadyExistsException>("Content already exists") {
            service.create(info)
        }
    }

    /**
     * Should delete existing content by its id
     */
    @Test
    fun `delete existing content`() {
        // Prepare
        val idToDelete = "13rt"

        // Action
        service.delete(idToDelete)

        // Assert
        assertThrows<ContentNotFoundException>("Content not found by id: $idToDelete") {
            service.findById(idToDelete)
        }
    }

    /**
     * Should delete existing content by section id
     */
    @Test
    fun `delete existing content by section id`() {
        // Prepare
        val sectionIdToDelete = 10L

        // Action
        service.deleteBySectionId(sectionIdToDelete)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Assert
        val content = service.findBySectionId(sectionIdToDelete)
        assertNull(content)
    }

    /**
     * Should throw "Not found exception" on attempt to delete by not existing section id
     */
    @Test
    fun `delete not existing content by section id`() {
        // Prepare
        val idToDelete = 666L

        // Action
        service.deleteBySectionId(idToDelete)

        // Assert
        val content = service.findBySectionId(idToDelete)
        assertNull(content)
    }

    /**
     * Should find existing content by its id
     */
    @Test
    fun `find existing content`() {
        // Prepare
        val expectedId = "13rt"
        val expected = Content(
            id = expectedId,
            sectionId = 4,
            text = "user text 12",
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
        val expectedSectionId = 4L
        val expected = Content(
            id = "13rt",
            sectionId = expectedSectionId,
            text = "user text 12",
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
     * Should update existing content
     */
    @Test
    fun `update existing content`() {
        // Prepare
        val contentId = "13rt"
        val expected = Content(
            id = contentId,
            sectionId = 4,
            text = "user text 12",
            references = mutableListOf(),
            tags = mutableListOf()
        )
        val info = makeContentInfo(expected)

        // Action
        service.update(info)

        // Prepare
        val content = service.findById(contentId)

        // Assert
        assertEquals(expected, content)
    }

    /**
     * Should throw ContentNotFoundException
     */
    @Test
    fun `update not existing content`() {
        // Prepare
        val expectedId = "abracadabra"
        val info = makeContentInfo(
            sectionId = 5,
            text = "updated text",
            references = mutableListOf(),
            tags = mutableListOf()
        )

        // Action & Assert
        assertThrows<ContentNotFoundException>("Content not found by id: $expectedId") {
            service.update(info)
        }
    }

    /**
     * Should remove existing content using contentService and save using contentVersionService
     */
    @Test
    fun `archive existing content`() {
        // Prepare
        val expectedSectionId = 1L

        // Action
        service.archiveBySectionId(expectedSectionId)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Prepare
        val content = service.findBySectionId(expectedSectionId)
        val oldContent = contentService.findBySectionId(expectedSectionId)
        val expected = Content(
            id = content?.id,
            sectionId = expectedSectionId,
            text = "general info text one",
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Assert
        assertEquals(expected, content)
        assertNull(oldContent)
    }

    /**
     * Should do nothing, if there is no such content found contentService
     */
    @Test
    fun `archive not existing content`() {
        // Prepare
        val expectedSectionId = 666L

        // Action
        service.archiveBySectionId(expectedSectionId)

        // Assert
        val content = service.findBySectionId(expectedSectionId)
        assertNull(content)
    }
}
