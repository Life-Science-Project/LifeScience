package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.content.maker.makeContentInfo
import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyArchivedException
import com.jetbrains.life_science.exception.section.SectionAlreadyPublishedException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.maker.makeSectionInfo
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/section/section_data.sql")
@Transactional
internal class SectionServiceTests {

    @Autowired
    lateinit var service: SectionService

    @Autowired
    lateinit var contentService: ContentVersionService

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
     * Should create new section with content correctly
     */
    @Test
    fun `create new section with content`() {
        // Prepare
        val contentInfo = makeContentInfo(
            id = "",
            sectionId = 0,
            text = "new text",
            references = mutableListOf(),
            tags = mutableListOf()
        )
        val info = makeSectionInfo(
            id = 0,
            name = "new name",
            order = 1,
            visible = true,
            contentInfo = contentInfo
        )

        // Action
        val section = service.create(info)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Prepare
        val content = contentService.findBySectionId(section.id)
        val expectedContent = Content(
            id = content?.id,
            sectionId = section.id,
            text = contentInfo.text,
            references = contentInfo.references.toMutableList(),
            tags = contentInfo.tags.toMutableList()
        )
        val expected = Section(
            id = section.id,
            name = info.name,
            order = info.order,
            visible = info.visible,
            published = false
        )

        // Assert
        assertEquals(expected, section)
        assertEquals(expectedContent, content)
    }

    /**
     * Should throw SectionNotFoundException after deleting existing section
     */
    @Test
    fun `delete existing section`() {
        // Prepare
        val idToDelete = 2L

        // Action
        service.deleteById(idToDelete)

        // Assert
        assertThrows<SectionNotFoundException>("Section not found by id: $idToDelete") {
            service.getById(2)
        }
    }

    /**
     * Should throw SectionNotFoundException after deleting not existing section
     */
    @Test
    fun `delete not existing section`() {
        // Prepare
        val idToDelete = 666L

        // Action & Assert
        assertThrows<SectionNotFoundException>("Section not found by id: $idToDelete") {
            service.deleteById(idToDelete)
        }
    }

    /**
     * Should return existing section correctly
     */
    @Test
    fun `get existing section`() {
        // Prepare
        val expectedId = 1L
        val expected = Section(
            id = expectedId,
            name = "general 1",
            order = 1,
            visible = true,
            published = false
        )

        // Action
        val section = service.getById(expectedId)

        // Assert
        assertEquals(expected, section)
    }

    /**
     * Should throw SectionNotFoundException on attempt to get not existing section
     */
    @Test
    fun `get not existing section`() {
        // Prepare
        val expectedId = 666L

        // Action & Assert
        assertThrows<SectionNotFoundException>("Section not found by id: $expectedId") {
            service.getById(expectedId)
        }
    }

    /**
     * Should return true on existing section
     */
    @Test
    fun `exist existing section`() {
        // Prepare
        val existingId = 1L

        // Action
        val exist = service.existsById(existingId)

        // Assert
        assertTrue(exist)
    }

    /**
     * Should return false on not existing section
     */
    @Test
    fun `exist not existing section`() {
        // Prepare
        val existingId = 666L

        // Action
        val exist = service.existsById(existingId)

        // Assert
        assertFalse(exist)
    }

    /**
     * Should update not published section correctly
     */
    @Test
    fun `update not published section`() {
        // Prepare
        val contentInfo = makeContentInfo(
            id = "",
            sectionId = 0,
            text = "new text",
            references = mutableListOf(),
            tags = mutableListOf()
        )
        val info = makeSectionInfo(
            id = 4,
            name = "updated name",
            order = 12,
            visible = false,
            contentInfo = contentInfo
        )

        // Action
        val section = service.update(info)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Prepare
        val content = contentService.findBySectionId(info.id)
        val expected = Section(
            id = info.id,
            name = info.name,
            order = info.order,
            visible = info.visible,
            published = section.published
        )
        val expectedContent = Content(
            id = content?.id,
            sectionId = contentInfo.sectionId,
            text = contentInfo.text,
            tags = contentInfo.tags.toMutableList(),
            references = contentInfo.references.toMutableList()
        )

        // Assert
        assertEquals(expected, section)
        assertEquals(expectedContent, content)
    }

    /**
     * Should throw SectionAlreadyPublishedException
     */
    @Test
    fun `update published section`() {
        // Prepare
        val contentInfo = makeContentInfo(
            id = "",
            sectionId = 0,
            text = "new text",
            references = mutableListOf(),
            tags = mutableListOf()
        )
        val info = makeSectionInfo(
            id = 2,
            name = "updated name",
            order = 12,
            visible = false,
            contentInfo = contentInfo
        )

        // Action & Assert
        assertThrows<SectionAlreadyPublishedException> {
            service.update(info)
        }
    }

    /**
     * Should throw SectionNotFoundException on attempt to update not existing section
     */
    @Test
    fun `update not existing section`() {
        // Prepare
        val contentInfo = makeContentInfo(
            id = "",
            sectionId = 0,
            text = "new text",
            references = mutableListOf(),
            tags = mutableListOf()
        )
        val info = makeSectionInfo(
            id = 12321,
            name = "updated name",
            order = 12,
            visible = false,
            contentInfo = contentInfo
        )

        // Action & Assert
        assertThrows<SectionNotFoundException>("Section not found by id: ${info.id}") {
            service.update(info)
        }
    }

    /**
     * Should publish existing not published section correctly
     */
    @Test
    fun `publish not published section`() {
        // Prepare
        val notPublishedId = 3L

        // Action
        service.publish(notPublishedId)

        // Wait
        Thread.sleep(1000)

        // Assert
        val section = service.getById(notPublishedId)
        val content = contentService.findBySectionId(notPublishedId)

        assertTrue(section.published)
        assertNull(content)
    }

    /**
     * Should throw SectionAlreadyPublishedException
     */
    @Test
    fun `publish published section`() {
        // Prepare
        val publishedId = 2L

        // Action & Assert
        assertThrows<SectionAlreadyPublishedException> {
            service.publish(publishedId)
        }
    }

    /**
     * Should throw SectionNotFoundException on attempt to publish not existing section
     */
    @Test
    fun `publish not existing section`() {
        // Prepare
        val notExistingId = 666L

        // Action & Assert
        assertThrows<SectionNotFoundException>("Section not found by id: $notExistingId") {
            service.publish(notExistingId)
        }
    }

    /**
     * Should throw SectionAlreadyArchivedException
     */
    @Test
    fun `archive not published section`() {
        // Prepare
        val notPublishedId = 4L

        // Action & Assert
        assertThrows<SectionAlreadyArchivedException> {
            service.archive(notPublishedId)
        }
    }

    /**
     * Should archive published section correctly
     */
    @Test
    fun `archive published section`() {
        // Prepare
        val publishedId = 2L

        // Action
        service.archive(publishedId)

        // Wait
        Thread.sleep(1000)

        // Assert
        val section = service.getById(publishedId)
        val content = contentService.findBySectionId(publishedId)

        assertFalse(section.published)
        assertEquals(section.id, content?.sectionId)
    }

    /**
     * Should throw SectionNotFoundException on attempt to archive not existing section
     */
    @Test
    fun `archive not existing section`() {
        // Prepare
        val notExistingId = 666L

        // Action & Assert
        assertThrows<SectionNotFoundException>("Section not found by id: $notExistingId") {
            service.archive(notExistingId)
        }
    }
}
