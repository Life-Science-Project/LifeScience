package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.category.search.PathUnit
import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.approach.ApproachSearchResult
import com.jetbrains.life_science.search.result.category.CategorySearchResult
import com.jetbrains.life_science.search.result.content.ContentSearchResult
import com.jetbrains.life_science.search.result.protocol.ProtocolSearchResult
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
            addPopulator("protocol", "elastic/protocol.json")
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
            CategorySearchResult(
                categoryId = 3, name = "catalog",
                listOf(
                    listOf(
                        PathUnit(1, "root")
                    )
                )
            ),
            CategorySearchResult(
                categoryId = 4, name = "catalog",
                listOf(
                    listOf(
                        PathUnit(1, "root"),
                        PathUnit(2, "catalog 2")
                    )
                )
            )
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
     * Should find expected protocols
     */
    @Test
    fun `protocols search`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "zeta",
            includeTypes = listOf(SearchUnitType.PROTOCOL),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            ProtocolSearchResult(publishProtocolId = 1, approachId = 1, name = "omega zeta"),
            ProtocolSearchResult(publishProtocolId = 2, approachId = 1, name = "zeta bi two")
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
                name = "catalog 1",
                paths = listOf(
                    emptyList()
                )
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
            CategorySearchResult(
                categoryId = 3, name = "catalog",
                paths = listOf(
                    listOf(
                        PathUnit(1, "root")
                    )
                )
            ),
            CategorySearchResult(
                categoryId = 4, name = "catalog",
                paths = listOf(
                    listOf(
                        PathUnit(1, "root"),
                        PathUnit(2, "catalog 2")
                    )
                )
            )
        )

        // Action
        val searchResult = service.search(searchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchResult.toSet())
    }

    /**
     * Should find expected categories with different letter cases request
     */
    @Test
    fun `uppercase test`() {
        // Prepare
        val searchQueryInfoLowercase = makeSearchQueryInfo(
            text = "fplc",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val searchQueryInfoUppercase = makeSearchQueryInfo(
            text = "FPLC",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val searchQueryInfoLowercaseFuzzy = makeSearchQueryInfo(
            text = "flpc",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val searchQueryInfoUppercaseFuzzy = makeSearchQueryInfo(
            text = "FLPC",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val searchQueryInfoMixedCase = makeSearchQueryInfo(
            text = "FpLc",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )

        val expectedResults = setOf(
            CategorySearchResult(
                categoryId = 1, name = "root",
                paths = listOf(emptyList())
            )
        )

        // Action
        val searchResultLowercase = service.search(searchQueryInfoLowercase)
        val searchResultUppercase = service.search(searchQueryInfoUppercase)
        val searchResultLowercaseFuzzy = service.search(searchQueryInfoLowercaseFuzzy)
        val searchResultUppercaseFuzzy = service.search(searchQueryInfoUppercaseFuzzy)
        val searchResultMix = service.search(searchQueryInfoMixedCase)

        // Assert
        assertEquals(expectedResults, searchResultLowercase.toSet())
        assertEquals(expectedResults, searchResultUppercase.toSet())
        assertEquals(expectedResults, searchResultLowercaseFuzzy.toSet())
        assertEquals(expectedResults, searchResultUppercaseFuzzy.toSet())
        assertEquals(expectedResults, searchResultMix.toSet())
    }

    @Test
    fun `suggest query test`() {
        // Prepare
        val searchQueryInfo = makeSearchQueryInfo(
            text = "cat",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val mixedCaseSearchQueryInfo = makeSearchQueryInfo(
            text = "CaT",
            includeTypes = listOf(SearchUnitType.CATEGORY),
            from = 0,
            size = 100
        )
        val expectedResults = setOf(
            CategorySearchResult(
                categoryId = 2,
                name = "catalog 1",
                paths = listOf(
                    emptyList()
                )
            ),
            CategorySearchResult(
                categoryId = 3, name = "catalog",
                paths = listOf(
                    listOf(
                        PathUnit(1, "root")
                    )
                )
            ),
            CategorySearchResult(
                categoryId = 4, name = "catalog",
                paths = listOf(
                    listOf(
                        PathUnit(1, "root"),
                        PathUnit(2, "catalog 2")
                    )
                )
            ),
            CategorySearchResult(
                categoryId = 5, name = "catalog 1",
                paths = listOf(
                    listOf(
                        PathUnit(1, "root"),
                        PathUnit(2, "catalog 2")
                    ),
                    listOf(
                        PathUnit(1, "root"),
                        PathUnit(2, "catalog 2"),
                        PathUnit(4, "catalog")
                    )
                )
            )
        )

        // Action
        val searchLowerCaseResult = service.suggest(searchQueryInfo)
        val searchMixedCaseResult = service.suggest(mixedCaseSearchQueryInfo)

        // Assert
        assertEquals(expectedResults, searchLowerCaseResult.toSet())
        assertEquals(expectedResults, searchMixedCaseResult.toSet())
    }
}
