package com.jetbrains.life_science.article.content.version.config

import com.jetbrains.life_science.article.content.index.IndexCoordinatesProvider
import com.jetbrains.life_science.util.createIndexIfNotExists
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.stereotype.Component

@Component
class ContentVersionConfig(
    val client: RestHighLevelClient
) : IndexCoordinatesProvider {

    private val contentVersionIndexName = "content_version"

    @Autowired
    fun createIndex() {
        createIndexIfNotExists(client, contentVersionIndexName)
    }

    override val indexCoordinates = IndexCoordinates.of(contentVersionIndexName)
}
