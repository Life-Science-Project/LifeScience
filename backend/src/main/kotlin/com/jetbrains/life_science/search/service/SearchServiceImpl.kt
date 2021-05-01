package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType
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

    lateinit var searchUnitServices: Map<SearchUnitType, UnitSearchService>

    @Autowired
    fun register(unitSearchService: List<UnitSearchService>) {
        searchUnitServices = unitSearchService.associateBy { service -> service.key }
    }

    override fun search(query: SearchQueryInfo): List<SearchResult> {
        val request = makeRequest(query)
        val response = getResponse(request)
        return response.hits.mapNotNull { processHit(it) }
    }

    private fun getResponse(request: SearchRequest): SearchResponse {
        return client.search(request, RequestOptions.DEFAULT)
    }

    private fun makeRequest(query: SearchQueryInfo): SearchRequest {
        val searchRequest = SearchRequest()
        val searchBuilder = SearchSourceBuilder()
        searchBuilder
            .query(QueryBuilders.matchQuery("text", query.text))
            .from(query.from)
            .size(query.size)
        searchRequest.source(searchBuilder)
        return searchRequest
    }

    private fun processHit(hit: SearchHit): SearchResult? {
        try {
            val content: Map<String, Any> = hit.sourceAsMap
            val id = hit.id
            val type = content.getOrThrow("_class") { "Type not found at hit: $hit" }
            val service = searchUnitServices[type] ?: return null
            return service.process(id, content)
        } catch (e: Exception) {
            logger.error("Error in search service", e)
            return null
        }
    }
}
