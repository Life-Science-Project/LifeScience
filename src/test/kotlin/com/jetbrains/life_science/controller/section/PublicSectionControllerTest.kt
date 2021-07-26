package com.jetbrains.life_science.controller.section

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
    "/scripts/section/section_data.sql",
    "/scripts/approach/public_approach_data.sql",
)
internal class PublicSectionControllerTest : ApiTest() {

    val pathPrefix = listOf("/api/approaches/public/", "/sections")

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
        val approachId = 1L
        val expectedView = SectionView(id = 4, name = "last", hidden = true, content = "user text 12")
        val sectionId = 4L

        // Action
        val section = getView<SectionView>(makePath(approachId, "//$sectionId"))

        // Assert
        assertEquals(expectedView, section)
    }

    fun makePath(approachId: Long, suffix: String): String {
        return pathPrefix[0] + approachId + pathPrefix[1] + suffix
    }
}
