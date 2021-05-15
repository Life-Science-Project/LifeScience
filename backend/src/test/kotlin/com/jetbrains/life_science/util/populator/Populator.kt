package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.DeleteByQueryRequest

internal class Populator(
    private val client: RestHighLevelClient,
    private val indexName: String,
    objectData: List<*>,
) {

    private val dataAsStringList: List<String> = objectData.map { ObjectMapper().writeValueAsString(it) }

    fun prepareData() {
        clear()
        populate()
    }

    private fun clear() {
        val request = DeleteByQueryRequest(indexName).setQuery(QueryBuilders.matchAllQuery())
        client.deleteByQuery(request, RequestOptions.DEFAULT)
    }

    fun createIndex() {
        val request = CreateIndexRequest(indexName)
        client.indices().create(request, RequestOptions.DEFAULT)
    }

    private fun populate() = dataAsStringList.forEach { content ->
        val request = IndexRequest(indexName).source(content, XContentType.JSON)
        client.index(request, RequestOptions.DEFAULT)
    }
}
