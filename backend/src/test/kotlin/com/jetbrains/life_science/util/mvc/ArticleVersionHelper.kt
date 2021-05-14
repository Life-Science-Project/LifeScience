package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.article.version.search.ArticleVersionSearchUnit
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.result.article.ArticleSearchResult
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get

class ArticleVersionHelper(
    private val mvc: MockMvc
) : BaseTestHelper() {

    private val searchHelper = SearchHelper(mvc)

    fun getVersion(id: Long): ArticleVersionView {
        val json = getForVersionById(id).andExpect { status { isOk() } }.andReturn().response.contentAsString
        return jsonMapper.readValue(json, ArticleVersionView::class.java)
    }

    private fun search(searchQueryDTO: SearchQueryDTO): List<ArticleSearchResult> {
        val searchResults = searchHelper.getSearchResults(searchQueryDTO)
        return searchResults.filter { it["typeName"].equals("Article") }
            .map {
                ArticleSearchResult(
                    (it.getOrThrow("versionId") as Int).toLong(),
                    it.getOrThrow("name"),
                    (it.getOrThrow("articleId") as Int).toLong()
                )
            }
    }

    fun existsOnce(searchResult: ArticleSearchResult, searchQueryDTO: SearchQueryDTO): Boolean {
        return count(searchResult, searchQueryDTO) == 1
    }

    fun notExistsInSearch(searchQueryDTO: SearchQueryDTO): Boolean {
        return search(searchQueryDTO).isEmpty()
    }

    private fun count(searchResult: ArticleSearchResult, searchQueryDTO: SearchQueryDTO): Int {
        return search(searchQueryDTO).count { it == searchResult }
    }

    private fun getForVersionById(id: Long): ResultActionsDsl {
        return mvc.get("/api/articles/versions/$id")
    }

}
