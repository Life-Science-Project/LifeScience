package com.jetbrains.life_science

import com.jetbrains.life_science.article.content.version.config.ContentVersionConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class LifeScienceApplicationTests {

    @MockBean
    lateinit var contentVersionConfig: ContentVersionConfig

    @Test
    fun contextLoads() {
    }
}
