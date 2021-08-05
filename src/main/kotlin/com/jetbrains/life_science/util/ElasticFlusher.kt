package com.jetbrains.life_science.util

import org.elasticsearch.action.admin.indices.flush.FlushRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Component

@Component
class ElasticFlusher(
    val highLevelClient: RestHighLevelClient
) {

    fun flush(timeSleepMillis: Long = 1000) {
        highLevelClient.indices().flush(FlushRequest(), RequestOptions.DEFAULT)
        Thread.sleep(timeSleepMillis)
    }
}
