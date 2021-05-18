package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder

internal class Populator(
    private val client: RestHighLevelClient,
    private val indexName: String,
    private val token: Class<*>,
    objectData: List<*>
) {

    private val objectMapper = jacksonObjectMapper()

    private val dataAsStringList: List<String> = objectData.map { objectMapper.writeValueAsString(it) }

    fun prepareData() {
        clear()
        populate()
    }

    private fun clear() {
        val result = searchForAllData()
        result.hits.forEach { hit ->
            client.delete(DeleteRequest(indexName, hit.id), RequestOptions.DEFAULT)
        }
    }

    private fun searchForAllData(): SearchResponse {
        val searchRequest = SearchRequest()
        val searchSourceBuilder = SearchSourceBuilder()
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
        searchRequest.source(searchSourceBuilder)
        searchRequest.indices(indexName)
        return client.search(searchRequest, RequestOptions.DEFAULT)
    }

    fun createIndex() {
        val request = CreateIndexRequest(indexName)
        client.indices().create(request, RequestOptions.DEFAULT)
    }

    private fun populate() = dataAsStringList.forEach { content ->
        val savingObjectMap = objectMapper.readValue(content, Map::class.java)
        val request = IndexRequest(indexName).source(content, XContentType.JSON)
        val id = savingObjectMap["id"]
        if (id != null) {
            request.id(id.toString())
        }
        client.index(request, RequestOptions.DEFAULT)
    }
}
