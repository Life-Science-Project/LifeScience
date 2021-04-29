package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.search.result.SearchResult
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getLogger
import com.jetbrains.life_science.util.getOrThrow
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    val client: RestHighLevelClient
) : SearchService {

    val logger = getLogger()

    lateinit var searchUnits: Map<String, UnitSearchService>

    @Autowired
    fun register(unitSearchService: List<UnitSearchService>) {
        searchUnits = unitSearchService.associateBy { service -> service.key }
    }

    override fun search(data: SearchQueryInfo): List<SearchResult> {
        val response = makeRequest(data)
        return processAllHits(response)
    }

    private fun processAllHits(response: SearchResponse): MutableList<SearchResult> {
        val resultList = mutableListOf<SearchResult>()
        response.hits.forEach { hit ->
            try {
                val searchResult = processHit(hit)
                resultList.add(searchResult)
            } catch (exception: IllegalStateException) {
                logger.error("Error in search service", exception)
            }
        }
        return resultList
    }

    private fun makeRequest(data: SearchQueryInfo): SearchResponse {
        val searchRequest = SearchRequest()
        val searchBuilder = SearchSourceBuilder()
        searchBuilder.query(QueryBuilders.matchQuery("text", data.query))
        searchRequest.source(searchBuilder)
        return client.search(searchRequest, RequestOptions.DEFAULT)
    }

    private fun processHit(hit: SearchHit): SearchResult {
        val content: Map<String, Any> = hit.sourceAsMap
        val id = hit.id
        val type = content.getOrThrow("_class") { "Type not found at hit: $hit" }
        val service = searchUnits.getOrThrow(type) { "Service not found for type: $type" }
        return service.process(id, content)
    }
}
