package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType

internal class Populator(
    private val client: RestHighLevelClient,
    private val indexName: String,
    objectData: List<Any>,
) {

    private val dataAsStringList: List<String> = objectData.map { ObjectMapper().writeValueAsString(it) }

    fun clear() {
        val request = DeleteIndexRequest(indexName)
        client.indices().delete(request, RequestOptions.DEFAULT)
    }

    fun createIndex() {
        val request = CreateIndexRequest(indexName)
        client.indices().create(request, RequestOptions.DEFAULT)
    }

    fun populate() = dataAsStringList.forEach { content ->
        val request = IndexRequest(indexName).source(content, XContentType.JSON)
        client.index(request, RequestOptions.DEFAULT)
    }
}
