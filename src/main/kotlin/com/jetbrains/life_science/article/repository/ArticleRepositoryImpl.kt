package com.jetbrains.life_science.article.repository

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.ml.PostDataRequest
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl(
    val client: RestHighLevelClient
): ArticleRepositoryCustom {


    override fun updateText(id: String, text: String) {
        val updateRequest = UpdateRequest("article", id)
        val doc = Json.encodeToString(mapOf("text" to text))
        updateRequest.doc(doc, XContentType.JSON)
        client.update(updateRequest, RequestOptions.DEFAULT)
    }

}
