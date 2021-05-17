package com.jetbrains.life_science.util.test

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jetbrains.life_science.search.result.article.ArticleSearchResult
import org.junit.jupiter.api.Test

class TTTest {


    @Test
    fun kek() {

        val mapper = jacksonObjectMapper()

        val data = mapper.writeValueAsString(mapper)
        mapper.readValue<ArticleSearchResult>(data)

    }

}
