package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.exception.section.SectionAlreadyArchivedException
import com.jetbrains.life_science.exception.section.SectionAlreadyPublishedException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.maker.makeSectionCreationInfo
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
        val info = makeSectionCreationInfo(
            id = 0,
            name = "new name",
            prevSection = null,
            visible = true,
            allSections = listOf()
        )

        // Action
        val section = service.create(info)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Assert
        assertEquals(info.name, section.name)
        assertEquals(0, section.order)
        assertEquals(info.hidden, section.hidden)
        assertFalse(section.published)
    }

    /**
     * Should create new section with content correctly
     */
    @Test
    fun `create new many sections`() {
        // Prepare
        val existingSection = Section(
            id = 1,
            name = "general 1",
            hidden = false,
            published = false,
            order = 0
        )
        val allSections = listOf(
            existingSection
        )
        val info = listOf(
            makeSectionCreationInfo(
                id = 0,
                name = "new name 1",
                prevSection = null,
                visible = true,
                allSections = listOf()
            ),
            makeSectionCreationInfo(
                id = 0,
                name = "new name 2",
                prevSection = existingSection,
                visible = true,
                allSections = listOf()
            ),
            makeSectionCreationInfo(
                id = 0,
                name = "new name 3",
                prevSection = null,
                visible = true,
                allSections = listOf()
            )
        )

        // Action
        val createdSections = service.createMany(info, allSections)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Assert
        val sections = mutableListOf<Section>()
        sections.add(service.getById(createdSections[0].id))
        sections.add(service.getById(createdSections[2].id))
        sections.add(existingSection)
        sections.add(service.getById(createdSections[1].id))

        assertEquals(info[0].name, sections[0].name)
        assertEquals(0, sections[0].order)
        assertEquals(info[1].name, sections[3].name)
        assertEquals(3, sections[3].order)
        assertEquals(info[2].name, sections[1].name)
        assertEquals(1, sections[1].order)
    }

    /**
     * Should throw SectionNotFoundException after deleting existing section
     */
    @Test
    fun `delete existing section`() {
        // Prepare
        val idToDelete = 2L

        // Action
        service.deleteById(idToDelete, listOf())

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
            service.deleteById(idToDelete, listOf())
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
            hidden = false,
            published = false
        )

        // Action
        val section = service.getById(expectedId)

        // Assert
        assertEquals(expected.id, section.id)
        assertEquals(expected.name, section.name)
        assertEquals(expected.order, section.order)
        assertEquals(expected.hidden, section.hidden)
        assertEquals(expected.published, section.published)
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
        val existingSection = service.getById(1)
        val info = makeSectionInfo(
            name = "updated name",
            prevSection = null,
            visible = false,
            content = "new text",
            allSections = listOf()
        )

        // Action
        val section = service.update(existingSection, info)

        // Wait
        elasticPopulator.flush()
        Thread.sleep(1000)

        // Prepare
        val content = contentService.findBySectionId(existingSection.id)
        val expected = Section(
            id = existingSection.id,
            name = info.name,
            order = 1,
            hidden = info.visible,
            published = existingSection.published
        )
        val expectedContent = Content(
            id = content?.id,
            sectionId = existingSection.id,
            text = info.content,
            tags = mutableListOf(),
            references = mutableListOf()
        )

        // Assert
        assertEquals(expected.id, section.id)
        assertEquals(expected.name, section.name)
        assertEquals(expected.order, section.order)
        assertEquals(expected.hidden, section.hidden)
        assertEquals(expected.published, section.published)
        assertEquals(expectedContent, content)
    }

    /**
     * Should throw SectionAlreadyPublishedException
     */
    @Test
    fun `update published section`() {
        // Prepare
        val existingSection = service.getById(2)
        val info = makeSectionInfo(
            name = "updated name",
            prevSection = null,
            visible = false,
            content = "new text",
            allSections = mutableListOf()
        )

        // Action & Assert
        assertThrows<SectionAlreadyPublishedException> {
            service.update(existingSection, info)
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
