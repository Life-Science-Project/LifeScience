package com.jetbrains.life_science.controller.section.protocol

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import javax.annotation.PostConstruct

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/protocol/public_protocol_data.sql",
)
internal class PublicProtocolSectionControllerTest : ApiTest() {

    val pathPrefix = listOf("/api/approaches/public/", "/protocols/", "/sections/")

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content", "elastic/section_public_approach_content.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    @Test
    fun `get existing section`() {
        // Prepare
        val protocolId = 1L
        val expectedView = SectionView(id = 1, name = "section", hidden = false, content = "user text 12")
        val sectionId = 1L
        val approachId = 1L

        // Action
        val section = getView<SectionView>(makePath(approachId, protocolId, sectionId))

        // Assert
        assertEquals(expectedView, section)
    }

    fun makePath(approachId: Long, protocolId: Long, sectionId: Long): String {
        return pathPrefix[0] + approachId + pathPrefix[1] + protocolId + pathPrefix[2] + sectionId
    }
}
