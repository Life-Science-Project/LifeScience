package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType

internal class Populator(
    private val client: RestHighLevelClient,
    private val indexName: String,
    objectData: List<*>,
) {

    private val objectMapper = jacksonObjectMapper()

    private val dataAsStringList: List<String> = objectData.map { objectMapper.writeValueAsString(it) }

    fun prepareData() {
        clear()
        createIndex()
        populate()
    }

    private fun clear() {
        val request = DeleteIndexRequest(indexName)
        client.indices().delete(request, RequestOptions.DEFAULT)
    }

    private fun createIndex() {
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
