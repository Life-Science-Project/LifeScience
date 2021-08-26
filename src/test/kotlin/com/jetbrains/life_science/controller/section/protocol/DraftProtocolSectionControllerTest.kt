package com.jetbrains.life_science.controller.section.protocol

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.protocol.draft.view.DraftProtocolView
import com.jetbrains.life_science.controller.section.dto.SectionCreationDTO
import com.jetbrains.life_science.controller.section.dto.SectionDTO
import com.jetbrains.life_science.controller.section.view.SectionView
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import javax.annotation.PostConstruct

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/protocol/draft_protocol_data.sql"
)
internal class DraftProtocolSectionControllerTest : ApiTest() {

    val pathPrefix = listOf("/api/protocols/draft/", "/sections")

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content_version", "elastic/section_draft_content_version.json")
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
        val protocolId = 2L
        val sectionId = 1L
        val expectedView = SectionView(id = sectionId, name = "draft_protocol_section", hidden = true, content = "user text 12")

        // Action
        val section = getViewAuthorized<SectionView>(makePath(protocolId, "//$sectionId"), accessToken)

        // Assert
        assertEquals(expectedView, section)
    }

    @Test
    fun `get existing section with wrong protocol id`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val protocolId = 666L
        val sectionId = 1L

        // Action
        val request = getAuthorized(makePath(protocolId, "//$sectionId"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_007, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `get not existing section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val protocolId = 2L
        val sectionId = 666L

        // Action
        val request = getAuthorized(makePath(protocolId, "//$sectionId"), accessToken)
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
        val protocolId = 2L

        // Action
        val created = postAuthorized<SectionView>(makePath(protocolId, "/"), sectionCreationDTO, accessToken)

        // Prepare
        flushChanges()
        val section = getViewAuthorized<SectionView>(makePath(protocolId, "//${created.id}"), accessToken)

        // Assert
        assertEquals(created.id, section.id)
        assertEquals(sectionCreationDTO.name, section.name)
        assertEquals(sectionCreationDTO.hidden, section.hidden)
        assertEquals(null, section.content)
    }

    @Test
    fun `create new section with not existing protocol`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val sectionCreationDTO = SectionCreationDTO(
            name = "created",
            hidden = false,
            prevSectionId = null
        )
        val protocolId = 666L

        // Action
        val request = postRequestAuthorized(makePath(protocolId, "/"), sectionCreationDTO, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_007, exceptionView.systemCode)
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
        val protocolId = 2L

        // Action
        val request = postRequestAuthorized(makePath(protocolId, "/"), sectionCreationDTO, accessToken)
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
        val protocolId = 2L

        // Action
        deleteRequestAuthorized(makePath(protocolId, "//$idToDelete"), accessToken)

        // Prepare
        flushChanges()
        val request = getAuthorized(makePath(protocolId, "//$idToDelete"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_006, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    @Test
    fun `delete section with not existing protocol`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val idToDelete = 1L
        val protocolId = 666L

        // Action
        val request = deleteRequestAuthorized(makePath(protocolId, "//$idToDelete"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        assertEquals(404_007, exceptionView.systemCode)
        assertEquals(emptyList<List<String>>(), exceptionView.arguments)
    }

    /**
     * Test check insertion section in the start of sections
     */
    @Test
    fun `update existing section insert in the begining`() {
        val protocolId = 3L
        val sectionId = 4L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba")

        patchRequestAuthorized(makePath(protocolId, "/$sectionId"), dto, accessToken)

        elasticPopulator.flush()
        Thread.sleep(1000)

        val section = getViewAuthorized<SectionView>(makePath(protocolId, "/$sectionId"), accessToken)

        // check section
        assertEquals(dto.name, section.name)
        assertEquals(dto.content, section.content)

        // check order
        assertCorrectOrder(protocolId, accessToken, listOf(4, 3))
    }

    /**
     * Test check insertion section in the end of sections
     */
    @Test
    fun `update existing section insert in the end`() {
        val protocolId = 3L
        val sectionId = 3L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 4)

        patchRequestAuthorized(makePath(protocolId, "/$sectionId"), dto, accessToken)

        elasticPopulator.flush()
        Thread.sleep(1000)

        val section = getViewAuthorized<SectionView>(makePath(protocolId, "/$sectionId"), accessToken)

        // check section
        assertEquals(dto.name, section.name)
        assertEquals(dto.content, section.content)

        // check order
        assertCorrectOrder(protocolId, accessToken, listOf(4, 3))
    }

    private fun assertCorrectOrder(protocolId: Long, accessToken: String, idsOrder: List<Long>) {
        val protocol = getViewAuthorized<DraftProtocolView>("/api/protocols/draft/$protocolId", accessToken)
        assertEquals(idsOrder, protocol.sections.map { it.id })
    }

    /**
     * Test checks for exception on attempt to update section with wrong approach id
     *
     * Expected 404 http code and 404_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `update section with wrong protocol id`() {
        val protocolId = 999L
        val sectionId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba")

        val request = patchRequestAuthorized(makePath(protocolId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_007, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    @Test
    fun `update not existing section`() {
        val protocolId = 2L
        val sectionId = 999L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequestAuthorized(makePath(protocolId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_006, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    @Test
    fun `update existing section with not existing previous section`() {
        val protocolId = 2L
        val sectionId = 1L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 999L)

        val request = patchRequestAuthorized(makePath(protocolId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404006, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    @Test
    fun `anonymous user section create`() {
        // Prepare
        val sectionCreationDTO = SectionCreationDTO(
            name = "created",
            hidden = false,
            prevSectionId = null
        )
        val protocolId = 1L

        // Action
        val request = postRequest(makePath(protocolId, "/"), sectionCreationDTO)
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `anonymous user section delete`() {
        // Prepare
        val protocolId = 1L

        // Action
        val request = deleteRequest(makePath(protocolId, "/1"))
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `anonymous user section update`() {
        val protocolId = 1L
        val sectionId = 5L
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequest(makePath(protocolId, "/$sectionId"), dto)
        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    @Test
    fun `regular user section create`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        // Prepare
        val sectionCreationDTO = SectionCreationDTO(
            name = "created",
            hidden = false,
            prevSectionId = null
        )
        val protocolId = 1L

        // Action
        val request = postRequestAuthorized(makePath(protocolId, "/"), sectionCreationDTO, accessToken)
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `regular user section delete`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        // Prepare
        val protocolId = 1L

        // Action
        val request = deleteRequestAuthorized(makePath(protocolId, "/1"), accessToken)
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `regular user section update`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        val protocolId = 1L
        val sectionId = 5L
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequestAuthorized(makePath(protocolId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    fun makePath(approachId: Long, suffix: String): String {
        return pathPrefix[0] + approachId + pathPrefix[1] + suffix
    }
}
