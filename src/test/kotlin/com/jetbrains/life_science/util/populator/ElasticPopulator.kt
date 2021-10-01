package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.elasticsearch.action.admin.indices.flush.FlushRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.io.ClassPathResource

class ElasticPopulator(
    private val highLevelClient: RestHighLevelClient
) {

    private val populators: MutableList<Populator> = mutableListOf()

    fun addPopulator(indexName: String, fileName: String) {
        val populator = Populator(
            client = highLevelClient,
            indexName = indexName,
            objectData = loadClasses(fileName)
        )
        populators.add(populator)
    }

    fun prepareData() {
        populators.forEach { it.prepareData() }
        flush()
        runBlocking { delay(ELASTIC_WAIT_TIME_MS) }
    }

    fun flush() {
        highLevelClient.indices().flush(FlushRequest(), RequestOptions.DEFAULT)
    }

    fun createIndexes() {
        populators.forEach { it.createIndex() }
    }

    private fun loadClasses(fileName: String): List<*> {
        val text = ClassPathResource(fileName).file.readText()
        return jacksonObjectMapper().readValue(text, List::class.java)
    }
}

private const val ELASTIC_WAIT_TIME_MS = 1500L
