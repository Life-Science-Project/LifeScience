package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.io.ClassPathResource

class ElasticPopulator(
    private val highLevelClient: RestHighLevelClient
) {

    private val populators: MutableList<Populator> = mutableListOf()

    fun addPopulator(indexName: String, fileName: String) {
        populators.add(loadPopulator(indexName, fileName))
    }

    fun prepareData() {
        populators.forEach { it.prepareData() }
    }

    fun createIndexes() {
        populators.forEach { it.createIndex() }
    }

    private fun loadPopulator(indexName: String, fileName: String): Populator {
        return Populator(highLevelClient, indexName, loadClasses(fileName))
    }

    private fun loadClasses(fileName: String): List<*> {
        val text = ClassPathResource(fileName).file.readText()
        return jacksonObjectMapper().readValue(text, List::class.java)
    }
}
