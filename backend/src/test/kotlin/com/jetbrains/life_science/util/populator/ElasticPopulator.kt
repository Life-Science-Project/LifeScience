package com.jetbrains.life_science.util.populator

import com.google.gson.Gson
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.io.ClassPathResource

class ElasticPopulator(
    private val highLevelClient: RestHighLevelClient
) {

    private val populators: MutableList<Populator<out Any>> = mutableListOf()

    fun <T> addPopulator(indexName: String, fileName: String) {
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

    private fun <T> loadPopulator(indexName: String, fileName: String): Populator<T> {
        return Populator(highLevelClient, indexName, loadClasses(fileName))
    }

    private fun <T> loadClasses(fileName: String): List<T> {
        val text = ClassPathResource(fileName).file.readText()
        return Gson().fromJson<List<T>>(text, List::class.java)
    }
}
