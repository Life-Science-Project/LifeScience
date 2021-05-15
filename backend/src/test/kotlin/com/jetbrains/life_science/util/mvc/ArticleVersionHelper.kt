package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.result.article.ArticleSearchResult
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.test.web.servlet.MockMvc

class ArticleVersionHelper(
    mvc: MockMvc
) : BaseTestHelper(mvc) {

    private val searchHelper = SearchHelper(mvc)

    private val viewToken = ArticleVersionView::class.java

    fun getVersion(id: Long): ArticleVersionView? {
        return getView("/api/articles/versions/$id", viewToken)
    }

    private fun search(searchQueryDTO: SearchQueryDTO): List<ArticleSearchResult> {
        val searchResults = searchHelper.getSearchResults(searchQueryDTO)
        return searchResults
            .filter { it["typeName"].equals("Article") }
            .map { mapToArticleVersionSearchResult(it) }
    }

    private fun mapToArticleVersionSearchResult(map: Map<String, String>): ArticleSearchResult {
        return ArticleSearchResult(
            (map.getOrThrow("versionId") as Int).toLong(),
            map.getOrThrow("name"),
            (map.getOrThrow("articleId") as Int).toLong()
        )
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
}
