package com.jetbrains.life_science.util.elastic

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.client.indices.GetIndexRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates

abstract class CustomIndexElasticsearchRepository(
    private val indexName: String,
) {

    @Autowired
    private fun createIndexIfNotExists(highLevelClient: RestHighLevelClient) {
        if (!indexExists(highLevelClient, indexName)) {
            val indexRequest = CreateIndexRequest(indexName)
            highLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT)
        }
    }

    private fun indexExists(highLevelClient: RestHighLevelClient, indexName: String): Boolean {
        val request = GetIndexRequest(indexName)
        return highLevelClient.indices().exists(request, RequestOptions.DEFAULT)
    }

    val indexCoordinates = IndexCoordinates.of(indexName)
}
