package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.exception.not_found.DraftApproachNotFoundException
import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.protocol.service.DraftProtocolService
import com.jetbrains.life_science.protocol.service.PublicProtocolService
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
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
@Sql(
    "/scripts/initial_data.sql",
    "/scripts/publisher/draft_approach_data.sql",
    "/scripts/publisher/draft_protocol_data.sql"
)
@Transactional
internal class PublisherServiceTest {

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
}
