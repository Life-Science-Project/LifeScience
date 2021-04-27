package com.jetbrains.life_science.article.version.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
internal class ArticleVersionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val jsonMapper = jacksonObjectMapper()

    /**
     * The controller should return a view of the available version.
     */
    @Test
    @Transactional
    fun `get article version`() {

    }

    /**
     * Trying to get a version not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    fun `get article version with wrong user`() {

    }

    /**
     * Trying to get a version with a non-existent identifier.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `get sections wrong section id`() {

    }

    /**
     * The controller should return views of the all available versions.
     */
    @Test
    @Transactional
    fun `get all versions`() {

    }

    /**
     * Trying to get a versions not owned by the user.
     * The controller should return a 403 status code.
     */
    @Test
    @Transactional
    fun `get all versions with wrong user`() {

    }

    /**
     * Trying to get versions with a non-existent identifier.
     * The controller should return a 404 status code.
     */
    @Test
    @Transactional
    fun `get all versions with wrong id`() {

    }

    /**
     * An attempt was made to create a version with an invalid article ID.
     * Controller should return 404 status code.
     */
    @Test
    @Transactional
    fun `create version with with wrong article id`() {

    }

    /**
     * Article versioning test
     */
    @Test
    @Transactional
    fun `create version`() {

    }




    private fun versionUrl(articleId: Int, versionId: Int): String {
        return "/api/articles/$articleId/versions/$versionId"
    }



}
