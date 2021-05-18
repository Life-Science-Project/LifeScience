
package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.DeleteByQueryRequest
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates

internal class Populator(
    private val client: RestHighLevelClient,
    private val indexName: String,
    objectData: List<*>
) {

    private val indexCoordinates = IndexCoordinates.of(indexName)

    private val objectMapper = jacksonObjectMapper()

    private val dataAsStringList: List<String> = objectData.map { objectMapper.writeValueAsString(it) }

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
        val savingObjectMap = objectMapper.readValue(content, Map::class.java)
        val request = IndexRequest(indexName).source(content, XContentType.JSON)
        val id = savingObjectMap["id"]
        if (id != null) {
            request.id(id.toString())
        }
        client.index(request, RequestOptions.DEFAULT)
    }
}
