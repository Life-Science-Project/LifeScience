package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.article.ApproachSearchResult
import com.jetbrains.life_science.search.result.category.CategorySearchResult
import com.jetbrains.life_science.search.result.content.ContentSearchResult
import com.jetbrains.life_science.search.service.maker.makeSearchQueryInfo
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/initial_data.sql")
@Transactional
internal class SearchServiceTest {

    @Autowired
    lateinit var service: SearchService

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    lateinit var elasticPopulator: ElasticPopulator

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("category", "elastic/category.json")
            addPopulator("content", "elastic/content.json")
            addPopulator("approach", "elastic/approach.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * Should find expected categories
     */
    @Test
    fun `categories search`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "alpha beta catalog",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            CategorySearchResult(categoryId = 3, name = "catalog"),
            CategorySearchResult(categoryId = 4, name = "catalog")
        )

        // Action
        val searchResult = service.search(searchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchResult.toSet())
    }

    /**
     * Should find expected approaches
     */
    @Test
    fun `approaches search`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "catalog three",
            includeTypes = listOf(SearchUnitType.APPROACH),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            ApproachSearchResult(publishApproachId = 3, name = "approach three"),
        )

        // Action
        val searchResult = service.search(searchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchResult.toSet())
    }

    /**
     * Should find expected content
     */
    @Test
    fun `content search`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "bradford",
            includeTypes = listOf(SearchUnitType.CONTENT),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            ContentSearchResult(
                id = "32281337",
                text = "bradford assay is my favourite method",
                sectionId = 15
            )
        )

        // Action
        val searchResults = service.search(searchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchResults.toSet())
    }

    /**
     * Should find expected content, category and approach
     */
    @Test
    fun `combined search`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "one",
            includeTypes = listOf(SearchUnitType.APPROACH, SearchUnitType.CATEGORY, SearchUnitType.CONTENT),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            ApproachSearchResult(
                publishApproachId = 1,
                name = "approach one"
            ),
            CategorySearchResult(
                categoryId = 2,
                name = "catalog 1"
            ),
            ContentSearchResult(
                id = "123",
                text = "general info text one",
                sectionId = 1
            )
        )

        // Action
        val searchResult = service.search(searchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchResult.toSet())
    }

    /**
     * Should find expected categories with fuzzy request
     */
    @Test
    fun `fuzzy search`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "alphas teta catalocs",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            CategorySearchResult(categoryId = 3, name = "catalog"),
            CategorySearchResult(categoryId = 4, name = "catalog")
        )

        // Action
        val searchResult = service.search(searchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchResult.toSet())
    }
}
