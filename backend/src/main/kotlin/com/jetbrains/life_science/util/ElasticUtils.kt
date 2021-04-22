package com.jetbrains.life_science.util

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.client.indices.GetIndexRequest

fun createIndexIfNotExists(highLevelClient: RestHighLevelClient, indexName: String) {
    if (!indexExists(highLevelClient, indexName)) {
        val indexRequest = CreateIndexRequest(indexName)
        highLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT)
    }
}

private fun indexExists(highLevelClient: RestHighLevelClient, indexName: String): Boolean {
    val request = GetIndexRequest(indexName)
    return highLevelClient.indices().exists(request, RequestOptions.DEFAULT)
}
