package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.io.ClassPathResource

class ElasticPopulator(
    private val highLevelClient: RestHighLevelClient
) {

    private val populators: MutableList<Populator> = mutableListOf()

    fun addPopulator(indexName: String, fileName: String, token: Class<*>) {
        val populator = Populator(
            client = highLevelClient,
            indexName = indexName,
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
