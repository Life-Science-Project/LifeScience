package com.jetbrains.life_science.category.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.article.primary.view.ArticleView
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.view.CategorySubcategoryView
import com.jetbrains.life_science.category.view.CategoryView
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.result.category.CategorySearchResult
import com.jetbrains.life_science.util.mvc.SearchHelper
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class CategoryControllerTest :
    ControllerTest<CategoryDTO, CategoryView>(CategoryView::class.java) {

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    init {
        apiUrl = "/api/categories"
    }

    lateinit var searchHelper: SearchHelper

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("category", "elastic/category.json")
        }
        searchHelper = SearchHelper(mockMvc)
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * Should get all categories with no parent
     */
    @Test
    internal fun `get root categories`() {
        val categories = getRootCategories()
        assertTrue(categories.isNotEmpty())
        for (category in categories) {
            assertNull(category.parentId)
        }
    }

    /**
     * Should get expected category
     */
    @Test
    internal fun `get existing category`() {
        val category = get(2)
        val expectedCategory = CategoryView(
            id = 2,
            parentId = 1,
            name = "child category 1",
            order = 2,
            subcategories = emptyList(),
            articles = emptyList()
        )
        assertEquals(expectedCategory, category)
    }

    /**
     * Should get expected category with versions
     */
    @Test
    internal fun `get existing category with versions`() {
        val category = get(1)
        val expectedCategory = CategoryView(
            id = 1,
            parentId = null,
            name = "root",
            order = 1,
            subcategories = listOf(
                CategorySubcategoryView(id = 2, name = "child category 1", order = 2),
                CategorySubcategoryView(id = 3, name = "child category 2", order = 3),
                CategorySubcategoryView(id = 4, name = "child category 3", order = 4)
            ),
            articles = listOf(
                ArticleView(
                    id = 1,
                    version = ArticleVersionView(
                        1, "master 1", 1,
                        listOf(
                            SectionLazyView(1, "name 1.1", 1),
                            SectionLazyView(2, "name 1.2", 2)
                        ),
                        State.PUBLISHED_AS_ARTICLE
                    ),
                    protocols = listOf(
                        ArticleVersionView(
                            7,
                            "version 1.2",
                            1,
                            listOf(SectionLazyView(6, "name 4", 1)),
                            State.PUBLISHED_AS_PROTOCOL
                        )
                    )
                )
            )
        )

        assertEquals(expectedCategory, category)
        assertInSearch(
            SearchQueryDTO("root"),
            CategorySearchResult(1, "root"),
            1
        )
    }

    /**
     * Should get status code 404
     */
    @Test
    internal fun `get non-existent category`() {
        assertNotFound("Category", getRequest(100))
    }

    /**
     * Should create new category
     */
    @Test
    internal fun `create category`() {
        val categoryDto = CategoryDTO("sample category", 1, 0)
        createCategory(categoryDto)

        assertInSearch(
            SearchQueryDTO("sample category"),
            CategorySearchResult(5, "sample category"),
            1
        )
    }

    /**
     * Should create new root category
     */
    @Test
    internal fun `create category with no parent`() {
        val rootCategoriesSize = getRootCategories().size
        val categoryDto = CategoryDTO("sample category", null, 0)
        createCategory(categoryDto)
        val rootCategories = getRootCategories()
        assertEquals(rootCategoriesSize + 1, rootCategories.size)
        for (category in rootCategories) {
            assertNull(category.parentId)
        }
    }

    /**
     * Should get status code 404, because parent doesn't exist
     */
    @Test
    internal fun `create category with non-existent parent`() {
        val categoryDto = CategoryDTO("null parent category", 100, 0)
        assertNotFound("Category", postRequest(categoryDto))
    }

    /**
     * Should update existing category
     */
    @Test
    internal fun `update existing category`() {
        val categoryDto = CategoryDTO("updated sample category", null, 150)
        updateCategory(1, categoryDto)
        assertInSearch(
            SearchQueryDTO("updated sample category"),
            CategorySearchResult(1, "updated sample category"),
            1
        )
    }

    /**
     * Updated category with null parent should be added to root
     */
    @Test
    internal fun `update category to root`() {
        val rootCategories = getRootCategories().size
        val categoryDto = CategoryDTO("updated sample category", null, 150)
        updateCategory(2, categoryDto)
        val categories = getRootCategories()
        assertEquals(rootCategories + 1, categories.size)
        for (category in categories) {
            assertNull(category.parentId)
        }
    }

    /**
     * Should get 404, because category doesn't exist
     */
    @Test
    internal fun `update non-existent category`() {
        val categoryDto = CategoryDTO("no category", null, 150)
        assertNotFound("Category", putRequest(100, categoryDto))
    }

    /**
     * Should update category parent and add subcategory to parent
     */
    @Test
    internal fun `update category parent`() {
        val oldParent = get(3)
        val categoryDto = CategoryDTO("updated sample category", oldParent.id, 150)
        updateCategory(2, categoryDto)
        val newParent = get(oldParent.id)
        assertEquals(oldParent.subcategories.size + 1, newParent.subcategories.size)
    }

    /**
     * Should get 404, because parent doesn't exist
     */
    @Test
    internal fun `update non-existent parent`() {
        val categoryDto = CategoryDTO("updated sample category", 8, 9)
        assertNotFound("Category", putRequest(1, categoryDto))
    }

    /**
     * Should delete existing category
     */
    @Test
    internal fun `delete existing category`() {
        delete(4)

        assertNotFound("Category", getRequest(4))
        assertInSearch(
            SearchQueryDTO("child category 3"),
            CategorySearchResult(4, "child category 3"),
            0
        )
    }

    /**
     * Should get 400, because deleting non-empty categories is prohibited
     */
    @Test
    internal fun `delete category with children`() {
        assertNotEmpty(deleteRequest(1))
    }

    /**
     * Should get 404, because category doesn't exist
     */
    @Test
    @Transactional
    internal fun `delete non-existent category`() {
        assertNotFound("Category", deleteRequest(100))
    }

    /**
     * Should forbid access to modifying methods
     */
    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        assertOk(rootCategoriesRequest())
        assertOk(getRequest(1))
        val categoryDto = CategoryDTO("sample category", 1, 0)
        assertForbidden(postRequest(categoryDto))
        assertForbidden(putRequest(1, categoryDto))
        assertForbidden(deleteRequest(3))
    }

    /**
     * Should forbid access to modifying methods
     */
    @Test
    @WithAnonymousUser
    internal fun `anonymous privileges`() {
        assertOk(rootCategoriesRequest())
        assertOk(getRequest(1))
        val categoryDto = CategoryDTO("sample category", 1, 0)
        assertUnauthenticated(postRequest(categoryDto))
        assertUnauthenticated(putRequest(1, categoryDto))
        assertUnauthenticated(deleteRequest(3))
    }

    private fun assertInSearch(searchQueryDTO: SearchQueryDTO, result: CategorySearchResult, count: Int = 0) {
        val hits = searchHelper.countHits(searchQueryDTO, result)
        assertEquals(count, hits)
    }

    private fun getRootCategories(): List<CategoryView> {
        val categories = rootCategoriesRequest()
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn().response.contentAsString
        return getViewsFromJson(categories)
    }

    private fun rootCategoriesRequest(): ResultActionsDsl {
        return mockMvc.get("$apiUrl/root")
    }

    private fun createCategory(dto: CategoryDTO) {
        val responseCategory = post(dto)
        assertNotNull(responseCategory.id)
        val savedCategory = get(responseCategory.id)
        val expectedCategory = CategoryView(responseCategory.id, dto.parentId, dto.name, dto.order, listOf(), listOf())
        assertEquals(expectedCategory, savedCategory)
    }

    private fun updateCategory(id: Long, dto: CategoryDTO) {
        val oldCategory = get(id)
        val responseCategory = put(id, dto)
        assertEquals(id, responseCategory.id)
        val updatedCategory = get(responseCategory.id)
        val expectedCategory = CategoryView(
            id = oldCategory.id,
            parentId = dto.parentId,
            name = dto.name,
            order = dto.order,
            subcategories = oldCategory.subcategories,
            articles = oldCategory.articles
        )
        assertEquals(expectedCategory, updatedCategory)
    }

    protected fun assertNotEmpty(result: ResultActionsDsl) {
        result.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value("Category not empty") }
        }
    }
}
