package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.container.protocol.search.service.ProtocolSearchUnitService
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
import com.jetbrains.life_science.edit_record.service.ProtocolEditRecordService
import com.jetbrains.life_science.exception.not_found.DraftApproachNotFoundException
import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql(
    "/scripts/initial_data.sql",
    "/scripts/publisher/draft_approach_data.sql",
    "/scripts/publisher/draft_protocol_data.sql",
    "/scripts/publisher/approach_edit_record_data.sql",
    "/scripts/publisher/protocol_edit_record_data.sql"
)
@Transactional
internal class PublisherServiceTest {

    @MockBean
    lateinit var protocolSearchUnitService: ProtocolSearchUnitService

    @MockBean
    lateinit var approachSearchUnitService: ApproachSearchUnitService

    @Autowired
    lateinit var service: PublisherService

    @Autowired
    lateinit var draftApproachService: DraftApproachService

    @Autowired
    lateinit var draftProtocolService: DraftProtocolService

    @Autowired
    lateinit var publicApproachService: PublicApproachService

    @Autowired
    lateinit var publicProtocolService: PublicProtocolService

    @Autowired
    lateinit var approachEditRecordService: ApproachEditRecordService

    @Autowired
    lateinit var protocolEditRecordService: ProtocolEditRecordService

    @Autowired
    lateinit var sectionService: SectionService

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
     * Should publish existing draft approach
     */
    @Test
    fun `publish existing draft approach`() {
        // Prepare
        val draftApproach = draftApproachService.get(1L)

        // Action
        val approach = service.publishDraftApproach(draftApproach)

        // Assert
        val publicApproach = publicApproachService.get(approach.id)
        assertEquals(draftApproach.name, publicApproach.name)
        assertEquals(draftApproach.categories.toSet(), publicApproach.categories.toSet())
        assertThrows<DraftApproachNotFoundException> {
            draftApproachService.get(draftApproach.id)
        }
        assertEquals(
            draftApproach.sections.map { it.id }.toSet(),
            publicApproach.sections.map { it.id }.toSet()
        )
        publicApproach.sections.forEach {
            assertTrue(it.published)
        }
    }

    /**
     * Should publish existing draft protocol
     */
    @Test
    fun `publish existing draft protocol`() {
        // Prepare
        val draftProtocol = draftProtocolService.get(1L)

        // Action
        val protocol = service.publishDraftProtocol(draftProtocol)

        // Assert
        val publicProtocol = publicProtocolService.get(protocol.id)
        assertEquals(draftProtocol.name, publicProtocol.name)
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolService.get(draftProtocol.id)
        }
        assertEquals(
            draftProtocol.sections.map { it.id }.toSet(),
            publicProtocol.sections.map { it.id }.toSet()
        )
        publicProtocol.sections.forEach {
            assertTrue(it.published)
        }
    }

    /**
     * Should publish existing approach edit record
     */
    @Test
    fun `publish existing approach edit record`() {
        // Prepare
        val approachEditRecord = approachEditRecordService.get(1L)

        // Action
        val approach = service.publishApproachEditRecord(approachEditRecord)

        // Assert
        approachEditRecord.createdSections.forEach {
            assertTrue(approach.sections.contains(it))
            assertTrue(it.published)
        }
        approachEditRecord.deletedSections.forEach {
            assertFalse(approach.sections.contains(it))
            assertThrows<SectionNotFoundException> {
                sectionService.getById(it.id)
            }
        }
    }

    /**
     * Should publish existing protocol edit record
     */
    @Test
    fun `publish existing protocol edit record`() {
        // Prepare
        val protocolEditRecord = protocolEditRecordService.get(1L)

        // Action
        val protocol = service.publishProtocolEditRecord(protocolEditRecord)

        // Assert
        protocolEditRecord.createdSections.forEach {
            assertTrue(protocol.sections.contains(it))
            assertTrue(it.published)
        }
        protocolEditRecord.deletedSections.forEach {
            assertFalse(protocol.sections.contains(it))
            assertThrows<SectionNotFoundException> {
                sectionService.getById(it.id)
            }
        }
    }
}
