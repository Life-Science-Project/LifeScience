package com.jetbrains.life_science.article.content.repository

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.springframework.stereotype.Repository

@Repository
class ContentRepositoryImpl(
    val client: RestHighLevelClient
) : ContentRepositoryCustom {

    override fun updateText(id: String, text: String) {
        val updateRequest = UpdateRequest("article", id)
        val doc = Json.encodeToString(mapOf("text" to text))
        updateRequest.doc(doc, XContentType.JSON)
        client.update(updateRequest, RequestOptions.DEFAULT)
    }
}
