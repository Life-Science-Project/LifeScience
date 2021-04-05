package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.container.service.ContainerService
import com.jetbrains.life_science.exceptions.ContainerNotFoundException
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.jdbc.datasource.AbstractDataSource
import org.mockito.ArgumentCaptor
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
internal class ArticleServiceImplTest {

    @Autowired
    lateinit var articleService: ArticleService

    @MockBean
    lateinit var articleRepository: ArticleRepository

    @BeforeEach
    @Sql("/scripts/test_trunc_data.sql")
    internal fun setUp() {

    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `article creation test`() {
        val articleInfo = mock<ArticleInfo> {
            on { text } doReturn "sample text"
            on { id } doReturn null
            on { containerId } doReturn 1L
            on { references } doReturn mutableListOf("r1", "r2")
            on { tags } doReturn mutableListOf("tag 1", "tag 2")
        }

        articleService.create(articleInfo)

        val argument = ArgumentCaptor.forClass(Article::class.java)

        verify(articleRepository, times(1)).save( argument.capture() )
        assertEquals(1L, argument.value.containerId)
        assertEquals("sample text", argument.value.text)
        assertEquals(mutableListOf("r1", "r2"), argument.value.references)
        assertEquals(mutableListOf("tag 1", "tag 2"), argument.value.tags)
    }

    @Test
    internal fun `article creation test with wrong container id`() {
        val articleInfo = mock<ArticleInfo> {
            on { text } doReturn ("sample text")
            on { id } doReturn (null)
            on { containerId } doReturn (-1L)
            on { references } doReturn (mutableListOf("r1", "r2"))
            on { tags } doReturn (mutableListOf("tag 1", "tag 2"))
        }

        assertThrows<ContainerNotFoundException> { articleService.create(articleInfo) }
    }

}
