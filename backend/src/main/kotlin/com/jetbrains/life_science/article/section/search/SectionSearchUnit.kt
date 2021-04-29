package com.jetbrains.life_science.article.section.search

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "section", createIndex = true)
@TypeAlias("Section")
data class SectionSearchUnit(

    val id: Long,

    var description: String

)
