package com.jetbrains.life_science.util.populator

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

    private fun loadClasses(fileName: String): List<Any> {
        val text = ClassPathResource(fileName).file.readText()
        return Gson().fromJson<List<Any>>(text, List::class.java)
    }
}
