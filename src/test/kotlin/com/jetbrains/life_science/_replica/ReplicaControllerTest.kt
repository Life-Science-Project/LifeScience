package com.jetbrains.life_science._replica

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/replicator/replicator_test_data.sql")
@Transactional
class ReplicaControllerTest : ApiTest() {

    val pathPrefix = "/api/replica"

    @Autowired
    lateinit var credentialsRepository: CredentialsRepository

    @Test
    fun `get all data`() {
        // Prepare data
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        // Action
        val data = getAuthorized("$pathPrefix/dump", accessToken).andExpect { status { isOk() } }
            .andReturn().response.contentAsString

        val expectedJSON = "{\"users\":[{\"id\":1,\"email\":\"admin@gmail.ru\",\"password\":" +
            "\"\$2a\$10\$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86\",\"role\":[\"ROLE_ADMIN\"],\"userData\"" +
            ":{\"firstName\":\"Alex\",\"lastName\":\"R\",\"doctorDegree\":false,\"about\":null,\"academicDegree\":1," +
            "\"orcid\":null,\"researchId\":null}}],\"category\":[{\"id\":1,\"name\":\"catalog 2\",\"aliases\":[]," +
            "\"parents\":[]},{\"id\":2,\"name\":\"catalog 2\",\"aliases\":[],\"parents\":[1]},{\"id\":3,\"name\":" +
            "\"catalog 2\",\"aliases\":[],\"parents\":[1,2]},{\"id\":4,\"name\":\"catalog 1\",\"aliases\":[],\"parents\"" +
            ":[]},{\"id\":5,\"name\":\"child 1-2\",\"aliases\":[],\"parents\":[4]}],\"publicApproaches\":[{\"name\":" +
            "\"approach 1\",\"aliases\":[],\"categories\":[4],\"sections\":[],\"protocols\":[{\"name\":\"first " +
            "published\",\"ownerId\":1,\"rating\":0,\"sections\":[],\"approachId\":1}],\"creationDateTime\":" +
            "\"2020-12-17T00:00\",\"ownerId\":1}],\"draftApproaches\":[{\"name\":\"approach 1\",\"aliases\":[]," +
            "\"categories\":[4],\"sections\":[],\"protocols\":[],\"creationDateTime\":\"2020-08-17T00:00\",\"ownerId" +
            "\":1}],\"draftProtocols\":[]}"

        // Assert
        Assertions.assertEquals(expectedJSON, data)
    }

    @Test
    fun `get all unauthorized`() {

        val request = getRequest("$pathPrefix/dump")

        val exceptionView = getApiExceptionView(403, request)

        Assertions.assertEquals(403_000, exceptionView.systemCode)
    }

    @Test
    fun `replicate data`() {
        // Prepare data
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        // Action
        postAuthorized("$pathPrefix/replicate", accessToken)

        // Assert
        assertTrue(credentialsRepository.existsById(1L))
        // TODO:: проверить вообще все данные
    }
}
