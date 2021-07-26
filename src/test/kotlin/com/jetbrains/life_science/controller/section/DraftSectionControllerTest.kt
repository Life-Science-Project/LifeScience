package com.jetbrains.life_science.controller.section

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.section.dto.SectionCreationDTO
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import javax.annotation.PostConstruct

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/section/section_data.sql",
    "/scripts/approach/draft_approach_data.sql",
    "/scripts/section/section_controller_data.sql"
)
internal class DraftSectionControllerTest : ApiTest() {

    val pathPrefix = listOf("/api/approaches/draft/", "/sections")

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content_version", "elastic/section_draft_approach_content_version.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    @Test
    fun `get existing section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val approachId = 1L
        val expectedView = SectionView(id = 1, name = "general 1", hidden = false, content = "user text 12")
        val sectionId = 1L

        // Action
        val section = getViewAuthorized<SectionView>(makePath(approachId, "//$sectionId"), accessToken)

        // Assert
        assertEquals(expectedView, section)
    }

    @Test
    fun `get existing section with wrong approach id`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val approachId = 666L
        val sectionId = 1L

        // Action
        val request = getAuthorized(makePath(approachId, "//$sectionId"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_003, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `get not existing section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val approachId = 1L
        val sectionId = 666L

        // Action
        val request = getAuthorized(makePath(approachId, "//$sectionId"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_006, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `create new section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val sectionCreationDTO = SectionCreationDTO(
            name = "created",
            hidden = false,
            prevSectionId = null
        )
        val approachId = 1L

        // Action
        val created = postAuthorized<SectionView>(makePath(approachId, "/"), sectionCreationDTO, accessToken)

        // Prepare
        flushChanges()
        val section = getViewAuthorized<SectionView>(makePath(approachId, "//${created.id}"), accessToken)

        // Assert
        assertEquals(created.id, section.id)
        assertEquals(sectionCreationDTO.name, section.name)
        assertEquals(sectionCreationDTO.hidden, section.hidden)
        assertEquals(null, section.content)
    }

    @Test
    fun `create new section with not existing approach`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val sectionCreationDTO = SectionCreationDTO(
            name = "created",
            hidden = false,
            prevSectionId = null
        )
        val approachId = 666L

        // Action
        val request = postRequestAuthorized(makePath(approachId, "/"), sectionCreationDTO, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_003, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `create new section with not existing prev section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val sectionCreationDTO = SectionCreationDTO(
            name = "created",
            hidden = false,
            prevSectionId = 666
        )
        val approachId = 1L

        // Action
        val request = postRequestAuthorized(makePath(approachId, "/"), sectionCreationDTO, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_006, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `delete existing section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val idToDelete = 1L
        val approachId = 1L

        // Action
        deleteRequestAuthorized(makePath(1L, "//$idToDelete"), accessToken)

        // Prepare
        flushChanges()
        val request = getAuthorized(makePath(approachId, "//$idToDelete"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_006, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `delete section with not existing approach`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val idToDelete = 1L
        val approachId = 666L

        // Action
        val request = deleteRequestAuthorized(makePath(approachId, "//$idToDelete"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_003, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `update existing section`() {
    }

    @Test
    fun `update section with wrong approach id`() {
    }

    @Test
    fun `update not existing section`() {
    }

    @Test
    fun `update existing section with not existing previous section`() {
    }

    @Test
    fun `anonymous user section create`() {
    }

    @Test
    fun `anonymous user section delete`() {
    }

    @Test
    fun `anonymous user section update`() {
    }

    @Test
    fun `regular user section create`() {
    }

    @Test
    fun `regular user section delete`() {
    }

    @Test
    fun `regular user section update`() {
    }

    fun makePath(approachId: Long, suffix: String): String {
        return pathPrefix[0] + approachId + pathPrefix[1] + suffix
    }
}
