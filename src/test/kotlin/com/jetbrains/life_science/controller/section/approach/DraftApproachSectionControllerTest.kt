package com.jetbrains.life_science.controller.section.approach

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachView
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
    "/scripts/section/section_data.sql",
    "/scripts/approach/draft_approach_data.sql",
    "/scripts/section/section_controller_data.sql"
)
internal class DraftApproachSectionControllerTest : ApiTest() {

    val pathPrefix = listOf("/api/approaches/draft/", "/sections")

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
        assertCreatedSuccessfully(created, section)
    }

    @Test
    fun `create many section`() {
        // Prepare
        val accessToken = loginAccessToken("email@email.ru", "password")
        val sectionCreationDTO1 = SectionCreationDTO(
            name = "created 1",
            hidden = false,
            prevSectionId = null
        )
        val sectionCreationDTO2 = SectionCreationDTO(
            name = "created 2",
            hidden = false,
            prevSectionId = null
        )
        val approachId = 2L

        // Action
        val createdSections = postAuthorized<List<SectionView>>(
            makePath(approachId, "/many"),
            listOf(sectionCreationDTO1, sectionCreationDTO2),
            accessToken
        )

        // Prepare
        flushChanges()
        val section1 = getViewAuthorized<SectionView>(makePath(approachId, "//${createdSections[0].id}"), accessToken)
        val section2 = getViewAuthorized<SectionView>(makePath(approachId, "//${createdSections[1].id}"), accessToken)

        // Assert
        assertCreatedSuccessfully(createdSections[0], section1)
        assertCreatedSuccessfully(createdSections[1], section2)
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

    /**
     * Test check insertion section in the start of sections
     */
    @Test
    fun `update existing section insert in the begining`() {
        val approachId = 1L
        val sectionId = 4L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba")

        patchRequestAuthorized(makePath(1, "/$sectionId"), dto, accessToken)

        elasticPopulator.flush()
        Thread.sleep(1000)

        val section = getViewAuthorized<SectionView>(makePath(approachId, "/$sectionId"), accessToken)

        // check section
        assertEquals(section.name, "section 1")
        assertEquals("aboba", section.content)

        // check order
        assertCorrectOrder(approachId, accessToken, listOf(4, 1, 5))
    }

    /**
     * Test check insertion section in the end of sections
     */
    @Test
    fun `update existing section insert in the end`() {
        val approachId = 1L
        val sectionId = 4L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 5)

        patchRequestAuthorized(makePath(1, "/$sectionId"), dto, accessToken)

        elasticPopulator.flush()
        Thread.sleep(1000)

        val section = getViewAuthorized<SectionView>(makePath(approachId, "/$sectionId"), accessToken)

        // check section
        assertEquals(section.name, "section 1")
        assertEquals("aboba", section.content)

        // check order
        assertCorrectOrder(approachId, accessToken, listOf(1, 5, 4))
    }

    /**
     * Test check insertion section in the middle of sections
     */
    @Test
    fun `update existing section insert in the middle`() {
        val approachId = 1L
        val sectionId = 5L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 1)

        patchRequestAuthorized(makePath(1, "/$sectionId"), dto, accessToken)

        elasticPopulator.flush()
        Thread.sleep(1000)

        val section = getViewAuthorized<SectionView>(makePath(approachId, "/$sectionId"), accessToken)

        // check section
        assertEquals(section.name, "section 1")
        assertEquals("aboba", section.content)

        // check order
        assertCorrectOrder(approachId, accessToken, listOf(1, 5, 4))
    }

    private fun assertCorrectOrder(approachId: Long, accessToken: String, idsOrder: List<Long>) {
        val approach = getViewAuthorized<DraftApproachView>("/api/approaches/draft/$approachId", accessToken)
        assertEquals(idsOrder, approach.sections.map { it.id })
    }

    /**
     * Test checks for exception on attempt to update section with wrong approach id
     *
     * Expected 404 http code and 404_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `update section with wrong approach id`() {
        val approachId = 999L
        val sectionId = 5L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequestAuthorized(makePath(approachId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404003, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    @Test
    fun `update not existing section`() {
        val approachId = 1L
        val sectionId = 999L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequestAuthorized(makePath(approachId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404006, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    @Test
    fun `update existing section with not existing previous section`() {
        val approachId = 1L
        val sectionId = 4L
        val accessToken = loginAccessToken("email@email.ru", "password")
        val dto = SectionDTO("section 1", true, "aboba", 999L)

        val request = patchRequestAuthorized(makePath(approachId, "/$sectionId"), dto, accessToken)
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
        val approachId = 1L

        // Action
        val request = postRequest(makePath(approachId, "/"), sectionCreationDTO)
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `anonymous user section delete`() {
        // Prepare
        val approachId = 1L

        // Action
        val request = deleteRequest(makePath(approachId, "/1"))
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `anonymous user section update`() {
        val approachId = 1L
        val sectionId = 5L
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequest(makePath(approachId, "/$sectionId"), dto)
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
        val approachId = 1L

        // Action
        val request = postRequestAuthorized(makePath(approachId, "/"), sectionCreationDTO, accessToken)
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `regular user section delete`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        // Prepare
        val approachId = 1L

        // Action
        val request = deleteRequestAuthorized(makePath(approachId, "/1"), accessToken)
        val exceptionView = getApiExceptionView(403, request)
        // Assert
        assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `regular user section update`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        val approachId = 1L
        val sectionId = 5L
        val dto = SectionDTO("section 1", true, "aboba", 1)

        val request = patchRequestAuthorized(makePath(approachId, "/$sectionId"), dto, accessToken)
        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    fun makePath(approachId: Long, suffix: String): String {
        return pathPrefix[0] + approachId + pathPrefix[1] + suffix
    }

    private fun assertCreatedSuccessfully(created: SectionView, section: SectionView) {
        assertEquals(created.id, section.id)
        assertEquals(created.name, section.name)
        assertEquals(created.hidden, section.hidden)
        assertEquals(null, section.content)
    }
}
