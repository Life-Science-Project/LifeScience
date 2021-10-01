package com.jetbrains.life_science.controller.generator.category

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.category.search.repository.CategorySearchUnitRepository
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import javax.annotation.PostConstruct

@Sql("/scripts/initial_data.sql")
class CategoryGeneratorControllerTests : ApiTest() {

    val pathPrefix = "/api/generator/category"

    @Autowired
    lateinit var categorySearchUnitRepository: CategorySearchUnitRepository

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient)
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    @Test
    fun `generate search units`() {
        // Prepare
        val accessToken = loginAccessToken("admin@gmail.ru", "password")
        val expected = setOf(
            ComparableCategorySearchUnit(
                id = 1,
                names = setOf("catalog 1"),
                context = setOf("catalog 1")
            ),
            ComparableCategorySearchUnit(
                id = 2,
                names = setOf("catalog 2"),
                context = setOf("catalog 2")
            ),
            ComparableCategorySearchUnit(
                id = 3,
                names = setOf("child 1-2"),
                context = setOf("child 1-2", "catalog 1")
            ),
            ComparableCategorySearchUnit(
                id = 4,
                names = setOf("catalog 2"),
                context = setOf("catalog 2")
            ),
            ComparableCategorySearchUnit(
                id = 5,
                names = setOf("catalog 2"),
                context = setOf("catalog 2")
            )
        )

        // Action
        patchAuthorized(pathPrefix, accessToken)
        elasticPopulator.flush()

        // Assert
        val searchUnits = categorySearchUnitRepository.findAll()
        assertEquals(
            expected,
            searchUnits.map {
                ComparableCategorySearchUnit(it.id, it.names.toSet(), it.context.toSet())
            }.toSet()
        )
    }

    data class ComparableCategorySearchUnit(
        val id: Long,
        val names: Set<String>,
        val context: Set<String>
    )
}
