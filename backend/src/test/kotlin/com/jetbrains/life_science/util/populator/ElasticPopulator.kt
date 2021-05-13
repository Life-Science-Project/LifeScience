package com.jetbrains.life_science.util.populator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
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
        clear()
        createIndexes()
        populate()
    }

    private fun clear() {
        populators.forEach { it.clear() }
    }

    private fun createIndexes() {
        populators.forEach { it.createIndex() }
    }

    private fun populate() {
        populators.forEach { it.populate() }
    }

    private fun loadPopulator(indexName: String, fileName: String): Populator {
        return Populator(highLevelClient, indexName, loadClasses(fileName))
    }

    private fun loadClasses(fileName: String): List<*> {
        val text = ClassPathResource(fileName).file.readText()
        return jacksonObjectMapper().readValue(text, List::class.java)
    }
}
