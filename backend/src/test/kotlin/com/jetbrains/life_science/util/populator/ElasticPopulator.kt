package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.io.ClassPathResource
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Component

@Component
class ElasticPopulator(
    private val elasticsearchOperations: ElasticsearchOperations,
    private val highLevelClient: RestHighLevelClient
) {

    private val populators: MutableList<Populator> = mutableListOf()

    fun addPopulator(indexName: String, fileName: String, token: Class<*>) {
        val populator = Populator(
            elasticsearchOperations = elasticsearchOperations,
            client = highLevelClient,
            indexName = indexName,
            token = token,
            objectData = loadClasses(fileName)
        )
        populators.add(populator)
    }

    fun prepareData() {
        populators.forEach { it.prepareData() }
        runBlocking { delay(400) }
    }

    fun createIndexes() {
        populators.forEach { it.createIndex() }
    }

    private fun loadClasses(fileName: String): List<*> {
        val text = ClassPathResource(fileName).file.readText()
        return jacksonObjectMapper().readValue(text, List::class.java)
    }
}
