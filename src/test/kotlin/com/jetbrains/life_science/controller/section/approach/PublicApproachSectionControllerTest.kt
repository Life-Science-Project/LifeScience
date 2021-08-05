package com.jetbrains.life_science.controller.section.approach

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
    "/scripts/approach/public_approach_data.sql",
)
internal class PublicApproachSectionControllerTest : ApiTest() {

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

    /**
     * Should return existing section view
     */
    @Test
    fun `get existing section`() {
        // Prepare
        val approachId = 2L
        val expectedView = SectionView(id = 1, name = "section", hidden = false, content = "user text 12")
        val sectionId = 1L

        // Action
        val section = getView<SectionView>(makePath(approachId, "//$sectionId"))

        // Assert
        assertEquals(expectedView, section)
    }

    /**
     * Should return 404_006 code
     */
    @Test
    fun `get not existing section test`() {
        val request = getRequest(makePath(1, "/666"))

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_006, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    fun makePath(approachId: Long, suffix: String): String {
        return pathPrefix[0] + approachId + pathPrefix[1] + suffix
    }
}
