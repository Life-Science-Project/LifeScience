package com.jetbrains.life_science.util.test

import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@Sql("/scripts/add_test_data.sql")
@Transactional
class UtilTest {

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @Test
    internal fun populateTest() {
        val elasticPopulator = ElasticPopulator(highLevelClient)
        with(elasticPopulator) {
            addPopulator<Content>("content", "elastic/content.json")
            addPopulator<Content>("content_version", "elastic/content_version.json")
            addPopulator<Content>("article", "elastic/article.json")
            addPopulator<Content>("section", "elastic/section.json")
            prepareData()
        }
    }
}
