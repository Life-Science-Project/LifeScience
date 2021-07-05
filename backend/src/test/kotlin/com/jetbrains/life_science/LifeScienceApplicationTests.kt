package com.jetbrains.life_science

import com.jetbrains.life_science.content.version.repository.ContentVersionRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class LifeScienceApplicationTests {

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    @Test
    fun contextLoads() {
    }
}
